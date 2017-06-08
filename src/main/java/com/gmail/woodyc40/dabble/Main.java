/*
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
package com.gmail.woodyc40.dabble;

import com.gmail.woodyc40.dabble.brain.Brain;
import com.gmail.woodyc40.dabble.dictionary.OxfordDictionary;
import com.gmail.woodyc40.dabble.dictionary.WordDefinition;
import com.gmail.woodyc40.dabble.parsing.Parser;
import com.gmail.woodyc40.dabble.parsing.Sentence;
import com.gmail.woodyc40.dabble.tags.UserInputted;
import com.gmail.woodyc40.dabble.training.Trainer;

import javax.annotation.concurrent.Immutable;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.gmail.woodyc40.dabble.util.UtilityMethods.*;

@Immutable
public final class Main {
    private static final String DEBUG = "-d";

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("SIGINT signal captured, terminating...")));
        List<String> ag = Arrays.asList(args);

        boolean doesDebugging = false;

        if (ag.contains(DEBUG)) {
            doesDebugging = true;
        }

        pl("                                                  ,---.'|              \n" +
                "    ,---,       ,---,           ,---,.     ,---,. |   | :       ,---,. \n" +
                "  .'  .' `\\    '  .' \\        ,'  .'  \\  ,'  .'  \\:   : |     ,'  .' | \n" +
                ",---.'     \\  /  ;    '.    ,---.' .' |,---.' .' ||   ' :   ,---.'   | \n" +
                "|   |  .`\\  |:  :       \\   |   |  |: ||   |  |: |;   ; '   |   |   .' \n" +
                ":   : |  '  |:  |   /\\   \\  :   :  :  /:   :  :  /'   | |__ :   :  |-, \n" +
                "|   ' '  ;  :|  :  ' ;.   : :   |    ; :   |    ; |   | :.'|:   |  ;/| \n" +
                "'   | ;  .  ||  |  ;/  \\   \\|   :     \\|   :     \\'   :    ;|   :   .' \n" +
                "|   | :  |  ''  :  | \\  \\ ,'|   |   . ||   |   . ||   |  ./ |   |  |-, \n" +
                "'   : | /  ; |  |  '  '--'  '   :  '; |'   :  '; |;   : ;   '   :  ;/| \n" +
                "|   | '` ,/  |  :  :        |   |  | ; |   |  | ; |   ,/    |   |    \\ \n" +
                ";   :  .'    |  | ,'        |   :   /  |   :   /  '---'     |   :   .' \n" +
                "|   ,.'      `--''          |   | ,'   |   | ,'             |   | ,'   \n" +
                "'---'                       `----'     `----'               `----'");
        lf();
        lf();
        pl("==== Dabble contextual dictionary ====");
        pl("AP Computer Science 2017 - Johnny Cao");
        OxfordDictionary.init();
        lf();
        if (!doesDebugging) {
            Trainer.init();
        }
        lf();
        pl("Press Ctrl-C to exit");
        lf();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            p("Define " + Brain.getInstance().statsLine() + ": ");
            String str = scanner.nextLine();

            if (doesDebugging && !str.contains(" ")) {
                OxfordDictionary.lookup(str).forEach(e -> {
                    pl(e.getWord() + " => ");
                    pl('\t' + e.getPartOfSpeech().name() + " - " + e.getDefinition().getInput());
                    lf();
                });
                continue;
            }

            Sentence sentence = new Sentence(str);
            sentence.get(UserInputted.class).setValue(Boolean.TRUE);

            List<WordDefinition> defs = Parser.parse(sentence);

            defs.forEach(e -> {
                pl(e.getWord() + " => ");
                pl('\t' + e.getPartOfSpeech().name() + " - " + e.getDefinition().getInput());
                lf();
            });
        }
    }
}