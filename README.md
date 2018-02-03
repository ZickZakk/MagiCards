# MagiCards
Card deck simulator for magicians. :black_joker: :sparkles: :tada:  
This tool simulates a card deck with an arbitrary amount of custom sized cards. It enables you to paint on any side of the card deck and shuffle the cards in a specified way.  
Magicians can use this tool to extend and refine the so called '[Unshuffled](http://geniimagazine.com/magicpedia/Unshuffled)' trick, which was popularized by [Paul Gertner](http://gertner.com/).

# Features
## Implemented
- [x] Paint on each of the four sides of a card deck
- [x] Shuffle the deck and its painting
- [x] List all shuffles that were applied to the deck in the shuffle history
- [x] Switch between different shuffles in the shuffle history
- [x] Reorder and remove shuffles from the shuffle history
- [x] Save the deck, its painting and the shuffle history and load it at a later point of time
- [x] Define you own custom shuffles
## Planned
- [ ] The following shuffles are implemented by default: 'in-Faro', 'out-Faro', 'Overhand' shuffle
- [ ] Check the status and errors of custom defined shuffles
- [ ] Define decks with an arbitrary amount of custom sized cards (currently only a 52 card standard deck is supported)

# Installation
Just download the [latest release](https://github.com/ZickZakk/MagiCards/releases/latest) and run the MagiCards.jar file.  
Java 9 with JavaFX is required to run this program. Recommended: Download and use [Java from Oracle](http://www.oracle.com/technetwork/java/javase/downloads/jre9-downloads-3848532.html).

# Define custom shuffles
In order to enable magicians to refine the 'Unshuffled' trick, MagiCards is extendable by new shuffle types.  
To create a new shuffle type you describe the shuffle in a *.shuffle file and place this file in the 'shuffles' folder beneath the MagiCards application. MagiCards automatically loads new shuffles from files each time you want to add a shuffle to your shuffle history.  
A 'example.shuffle' file is provided in the 'shuffles' folder of each release. It simply reverses the deck order of a 52 cards deck without flip or turn. You can check it out, see how it works and delete it, if you don't want to see it in the shuffle adding dialog.

## small_example.shuffle
```xml
<?xml version="1.0" encoding="utf-8" ?>
<XMLShuffle name="TestShuffle" deckSize="4">
    <XMLShuffleEntries>
        <XMLShuffleEntry source="0" destination="3" flip="false" turn="false"/>
        <XMLShuffleEntry source="1" destination="2" flip="true" turn="false"/>
        <XMLShuffleEntry source="2" destination="1" flip="false" turn="true"/>
        <XMLShuffleEntry source="3" destination="0" flip="true" turn="true"/>
    </XMLShuffleEntries>
</XMLShuffle>
```
## How to define a custom shuffle
To define your custom shuffle:
1. Enter a unique shuffle name as the 'name' attribute of the XMLShuffle tag
1. Enter the size of the deck your shuffle works for as the 'deckSize' attribute of the XMLShuffle tag
1. For each card of your deck (defined by the deck size you specified):
   1. Create a self enclosed 'XMLShuffleEntry' tag
   1. Enter the start index of the card as the 'source' attribute of the XMLShuffleEntry tag. Card indexes are zero based.
   1. Enter the destination index of the card as the 'destination' attribute of the XMLShuffleEntry tag. Card indexes are zero based.
   1. Define if the card is flipped during the shuffle by entering 'true' or 'false' as the 'flip' attribute of the XMLShuffleEntry tag.
   1. Define if the card is turned during the shuffle by entering 'true' or 'false' as the 'turn' attribute of the XMLShuffleEntry tag.
1. Save the file as [name].shuffle in the shuffles folder beneath the MagiCards application
   
## Common mistakes when creating a custom shuffle
Your shuffle is not listed when you want to add a new shuffle to the shuffle history or doesn't work as expected?  
Please check that:  
- You have no spelling errors inside your *.shuffle file
- The name of your custom shuffle is unique
- Every source and destination card index is used for the deck size you specified
- No source or destination card index is used twice
- Flip and Turn attributes are only 'true' or 'false'
- You placed the shuffle file in the 'shuffles' folder beneath the MagiCards application

# Participate
Found a bug? You have two options  
1. Fork my code and fix the bug by yourself. Submit a pull request afterwards, we will discuss your changes and merge your fix into my code.
2. If you can't fix the bug by yourself, you can submit an [issue](https://github.com/ZickZakk/MagiCards/issues). Please check, that no one else already filed the same bug by searching the open and closed issues. We can then discuss your issue. If I or someone else finds the time, we will try to fix the bug.

# Support
MagiCards is open source and the usage is free of charge. If you use my tool, liked it or even created your own magic trick with it I'm very happy.  
If you really want to support or thank me in a tangible way, you can [donate](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=ZM2L9MRB9VTSJ) for my next pizza. :pizza:
