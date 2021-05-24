# dabble

Final project for APCS

I made a command-line dictionary that makes a horrendous attempt to quantify the context of English sentences in order to define individual words.

I'm particularly proud of a [binary search implementation](https://github.com/caojohnny/dabble/blob/master/src/main/java/com/gmail/woodyc40/dabble/dictionary/OxfordDictionary.java) I wrote to look through a RandomAccessFile for the word, taking into consideration the non-machine-friendliness of the text file I used. Looking back, I probably should have parsed it all in the first place to make it more machine friendly, but I didn't want to deal with the hassle of distributing the text file itself.

Slides for my presentation can be found [here](https://docs.google.com/presentation/d/1ZQi5xQeHXQslrVh0bdv6cekkwRPbxHdnzIcEd24bOMI/edit?usp=sharing)
