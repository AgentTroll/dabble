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

import com.gmail.woodyc40.dabble.dictionary.OxfordDictionary;
import com.gmail.woodyc40.dabble.lexing.Sentence;
import com.gmail.woodyc40.dabble.parsing.Parser;

import java.util.Scanner;

import static com.gmail.woodyc40.dabble.util.UtilityMethods.*;

public final class Main {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> pl("SIGINT captured, exiting...")));
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
        pl("Press Ctrl-C to exit");
        lf();

        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();

        while (true) {
            p("Define: ");
            String str = scanner.nextLine();

            Sentence sentence = new Sentence(str);
            parser.parse(sentence);

            lf();
        }
    }
}