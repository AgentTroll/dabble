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
package com.gmail.woodyc40.dabble.brain;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.gmail.woodyc40.dabble.util.UtilityMethods.p;
import static com.gmail.woodyc40.dabble.util.UtilityMethods.pl;

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
        // Actually the oxford .txt file is not wholly UTF-8
        // most of it is, except for the special symbols
        // which are UTF-16 (? what's more, there are also
        // some 3-byte spechars)
        int depth = 0;
        List<WordDefinition> aggregateList = new ArrayList<>(1);

        try {
            long min = 0L;
            long max = OxfordDictionary.dictionary.length();
            while (true) {
                char curItr = word.charAt(depth);
                long mid = (min + max) / 2L;
                OxfordDictionary.dictionary.seek(mid);

                // Go to the beginning of the "mid" line
                mid = backtrack(mid);

                String line = dictionary.readLine();
                String[] split = line.split("  ");
                String dictWord = split[0];

                // If not narrowed down enough, decrement
                // the depth and try again
                if (!dictWord.startsWith(word.substring(0, depth))) {
                    depth--;
                    continue;
                }

                // If the definition is too short, then the
                // word will always come after, set to the
                // min value to raise min to mid

                // File pointer already advanced after
                // reading the line
                char letter = depth >= dictWord.length() ? Character.MIN_VALUE : dictWord.charAt(depth);
                if (letter > curItr) {
                    max = dictionary.getFilePointer();
                } else if (curItr > letter) {
                    min = dictionary.getFilePointer();
                } else {
                    if (tryAggregate(word, dictWord, split[1], aggregateList)) {
                        pl("Aggregated #" + dictWord);
                        return aggregateList;
                    }

                    // If all the letter at the current
                    // depth already matches, then look at
                    // the next letter
                    depth++;

                    // If the depth is longer than the word
                    // length, no further results are found
                    // b/c all lower combos have been cmp'd
                    if (depth == word.length()) {
                        return Collections.emptyList();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean tryAggregate(String word, String dictWord, String def, List<WordDefinition> aggregate)
            throws IOException {
        char lc = dictWord.charAt(dictWord.length() - 1);
        if (lc > '0' && lc < '9') {
            dictWord = dictWord.replace(lc, ' ').trim();
        } else {
            if (word.equalsIgnoreCase(dictWord)) {
                compose(def, aggregate);
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
                dictWord = dictionary.readLine();
                String[] split = dictWord.split("  ");
                dictWord = split[0];
                lc = dictWord.charAt(dictWord.length() - 1);
                // as of the  moment there are no 2digits
                // but this will break (as in literally)
                // if there are
                if (lc > '0' && lc < '9') {
                    dictWord = dictWord.replace(lc, ' ').trim();
                } else {
                    break;
                }

                if (word.equalsIgnoreCase(dictWord)) {
                    compose(split[1], aggregate);
                } else {
                    break;
                }

                dictionary.seek(idx - 3);
                idx = backtrack(idx - 3);
            }

            // Look for occurrances after
            dictionary.seek(start + 1);
            while (true) {
                dictWord = dictionary.readLine();
                String[] split = dictWord.split("  ");
                dictWord = split[0];
                lc = dictWord.charAt(dictWord.length() - 1);
                if (lc > '0' && lc < '9') {
                    dictWord = dictWord.replace(lc, ' ').trim();
                } else {
                    break;
                }

                if (word.equalsIgnoreCase(dictWord)) {
                    compose(split[1], aggregate);
                } else {
                    break;
                }

                dictionary.seek(dictionary.getFilePointer() + 1);
            }

            return true;
        }

        return false;
    }

    private static void compose(String line, List<WordDefinition> aggregate) {
        aggregate.add(new WordDefinition(line)); // TODO
    }

    private static long backtrack(long currentIdx) throws IOException {
        while (true) {
            char c = (char) OxfordDictionary.dictionary.read();
            if (c != '\n' && currentIdx != 0) {
                currentIdx--;
                dictionary.seek(currentIdx);
            } else {
                break;
            }
        }

        return currentIdx;
    }
}