/**
 * Dabble contextual dictionary - Copyright 2017 Johnny Cao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gmail.woodyc40.dabble.dictionary;

import com.gmail.woodyc40.dabble.parsing.Sentence;

import javax.annotation.concurrent.NotThreadSafe;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gmail.woodyc40.dabble.util.UtilityMethods.p;
import static com.gmail.woodyc40.dabble.util.UtilityMethods.pl;
import static com.gmail.woodyc40.dabble.util.UtilityMethods.strip;

@NotThreadSafe
public final class OxfordDictionary {
    private static final RandomAccessFile dictionary;

    static {
        Path dir = Paths.get(System.getProperty("user.dir")).resolve("Dabble_files");
        Path dict = dir.resolve("Oxford English Dictionary.txt");

        try {
            p("Creating " + dir + "... ");
            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
            }
            pl("Done.");

            if (!Files.exists(dict)) {
                URL src = new URL("https://raw.githubusercontent.com/sujithps/Dictionary/master/Oxford%20English%20Dictionary.txt");
                p("Resolving " + src + "... ");
                URLConnection con = src.openConnection();

                try (InputStream in = con.getInputStream()) {
                    Files.copy(in, dict);
                }

                pl("Done.");
            } else {
                pl("Detected files already in place; If it's corrupt, simply delete it and re-run.");
            }

            dictionary = new RandomAccessFile(dict.toFile(), "r");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private OxfordDictionary() {
    }

    // Just used to coerce the static init block to run
    public static void init() {
    }

    public static List<WordDefinition> lookup(String word) {
        word = word.toLowerCase(); // canonical casing
        List<WordDefinition> aggregateList = new ArrayList<>(1);

        try {
            long min = 0L;
            long max = dictionary.length();
            while (min <= max) {
                long mid = (min + max) / 2L;
                dictionary.seek(mid);

                // Go to the beginning of the "mid" line
                backtrack(mid);

                String line = readLine();
                String[] split = line.split("  ");
                String dictWord;
                if (split.length < 2 && !line.isEmpty()) {
                    dictWord = line.substring(0, line.indexOf(' '));
                } else {
                    dictWord = split[0].toLowerCase();
                }

                int letter = -1;
                int actual = -1;

                dictWord = strip(dictWord);
                for (int i = 0; i < word.length() && i < dictWord.length(); i++) {
                    char c = dictWord.charAt(i);
                    char c1 = word.charAt(i);
                    if (c != c1) {
                        letter = c;
                        actual = c1;
                        break;
                    }
                }

                if (letter == -1 && actual == -1 && dictWord.length() < word.length()) {
                    actual = 0;
                }

                if (letter > actual) {
                    mid -= 3;
                    dictionary.seek(mid);
                    max = backtrack(mid);
                } else if (actual > letter) {
                    min = dictionary.getFilePointer();
                } else {
                    if (tryAggregate(word, dictWord, split[1], aggregateList)) {
                        return aggregateList;
                    } else {
                        if (letter == -1 && actual == -1) {
                            letter = dictWord.length();
                            actual = word.length();

                            if (letter > actual) {
                                mid -= 3;
                                dictionary.seek(mid);
                                max = backtrack(mid);
                            } else if (actual > letter) {
                                min = dictionary.getFilePointer();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Collections.emptyList();
    }

    private static boolean tryAggregate(String word, String dictWord, String def, List<WordDefinition> aggregate)
            throws IOException {
        char lc = dictWord.charAt(dictWord.length() - 1);
        if (isNum(lc)) {
            dictWord = dictWord.replace(lc, ' ').trim();
        } else {
            if (word.equalsIgnoreCase(dictWord)) {
                compose(word, def, null, aggregate);
                return true;
            }
        }

        if (word.equalsIgnoreCase(dictWord)) {
            long start = dictionary.getFilePointer();
            long idx = start - 3;

            // Look for current + previous occurrences
            dictionary.seek(idx);
            idx = backtrack(idx);
            while (true) {
                dictWord = readLine();
                String[] split = dictWord.split("  ");
                dictWord = split[0];
                lc = dictWord.charAt(dictWord.length() - 1);
                // as of the  moment there are no 2digits
                // but this will break (as in literally)
                // if there are
                if (isNum(lc)) {
                    dictWord = dictWord.replace(lc, ' ').trim();
                }

                if (word.equalsIgnoreCase(dictWord)) {
                    compose(word, split[1], null, aggregate);
                } else {
                    break;
                }

                dictionary.seek(idx - 3);
                idx = backtrack(idx - 3);
            }

            // Look for occurrences after
            dictionary.seek(start + 1);
            while (true) {
                dictWord = readLine();
                String[] split = dictWord.split("  ");
                dictWord = split[0];
                lc = dictWord.charAt(dictWord.length() - 1);
                if (isNum(lc)) {
                    dictWord = dictWord.replace(lc, ' ').trim();
                }

                if (word.equalsIgnoreCase(dictWord)) {
                    compose(word, split[1], null, aggregate);
                } else {
                    break;
                }

                dictionary.seek(dictionary.getFilePointer() + 1);
            }

            return true;
        }

        return false;
    }

    private static void compose(String word, String line, Matcher match, List<WordDefinition> aggregate) {
        // Format:
        // (—) - Presence indicates change in POS
        // POS - Part of speech
        // (#) - Presence indicates multiple definitions
        // (A) - Presence indicates multiple definitions
        // Definition
        Pattern pat = Pattern.compile("—\\w+\\.");
        String[] split = pat.split(line);

        if (split.length > 1) {
            Matcher matcher = pat.matcher(line);
            for (int i = 1; i < split.length; i++) {
                compose(word, split[i], matcher, aggregate);
            }
            return;
        }

        split = line.split(Pattern.quote(" "));

        StringBuilder def = null;
        PartOfSpeech pos = null;

        boolean bracket = false;

        for (String cur : split) {
            if (cur.isEmpty()) {
                continue;
            }

            char c0 = cur.charAt(0);

            if (pos == null) {
                String group;
                if (match == null) {
                    group = cur;
                } else {
                    if (!match.find()) {
                        throw new RuntimeException("Error occurred grabbing point of view");
                    }
                    group = match.group().replaceAll("—", "");
                }

                for (PartOfSpeech val : PartOfSpeech.values()) {
                    if (val.toString().equals(group.toLowerCase())) {
                        pos = val;
                        break;
                    }
                }

                if (pos == null) {
                    if (!group.endsWith(".")) {
                        continue;
                    }
                    throw new RuntimeException("Error occurred grabbing point of view for \"" + line + "\" - " + group);
                }

                def = new StringBuilder();
                continue;
            }

            if (c0 == ']' && bracket) {
                bracket = false;
                continue;
            }

            if (c0 == '[' || bracket) {
                bracket = true;
                continue;
            }

            if ((c0 >= 'A' && c0 <= 'Z' || isNum(c0)) && cur.length() == 1) {
                if (def.length() > 0) {
                    aggregate.add(new WordDefinition(word, new Sentence(def.toString().trim()), pos, match != null));
                    def = new StringBuilder();
                }
            } else {
                def.append(cur).append(' ');
            }
        }

        aggregate.add(new WordDefinition(word, new Sentence(def.toString().trim()), pos, match != null));
    }

    private static long backtrack(long currentIdx) throws IOException {
        while (true) {
            char c = (char) dictionary.read();
            if (c != '\n' && currentIdx != 0) {
                currentIdx--;
                dictionary.seek(currentIdx);
            } else {
                break;
            }
        }

        return currentIdx;
    }

    // https://stackoverflow.com/questions/1008802/converting-symbols-accent-letters-to-english-alphabet
    // https://stackoverflow.com/questions/9964892/how-to-read-utf8-encoded-file-using-randomaccessfile
    private static String readLine() throws IOException {
        String line = dictionary.readLine();
        String str = new String(line.getBytes("ISO-8859-1"), "UTF-8");
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    private static boolean isNum(char c) {
        return c >= '1' && c <= '9';
    }
}