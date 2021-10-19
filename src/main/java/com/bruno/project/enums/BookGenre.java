package com.bruno.project.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum BookGenre {

    ACTION(0, "Action"),
    ADVENTURE(1, "Adventure"),
    ANTHOLOGY(2, "Anthology"),
    CLASSIC(3, "Classic"),
    COMICANDGRAPHICNOVEL(4, "Comic and Graphic Novel"),
    CRIMEANDDETECTIVE(5, "Crime and Detective"),
    DRAMA(6, "Drama"),
    FABLE(7, "Fable"),
    FAIRYTALE(8, "Fairy Tale"),
    FANTASY(9, "Fantasy"),
    HORROR(10, "Horror"),
    HUMOR(11, "Humor"),
    LEGEND(12, "Legend"),
    MAGICALREALISM(13, "Magical Realism"),
    MYSTERY(14, "Mystery"),
    MYTHOLOGY(15, "Mythology"),
    FANFICTION(16, "Fan Fiction"),
    HISTORICALFICTION(17, "Historical Fiction"),
    REALISTICFICTION(18, "Realistic Fiction"),
    SCIENCEFICTION(19, "Science Fiction"),
    ROMANCE(20, "Romance"),
    SATIRE(21, "Satire"),
    SCIFI(22, "Sci-Fi"),
    SHORTSTORY(23, "Short Story"),
    SUSPENSE(24, "Suspense"),
    THRILLER(25, "Thriller"),
    BIOGRAPHY(26, "Biography"),
    AUTOBIOGRAPHY(27, "Autobiography"),
    ESSAY(28, "Essay"),
    MEMOIR(29, "Memoir"),
    NARRATIVENONFICTION(30, "Narrative Non-Fiction"),
    PERIODICALS(31, "Periodicals"),
    REFERENCEBOOKS(32, "Reference Books"),
    SELFHELPBOOK(33, "Self-Help Book"),
    SPEECH(34, "Speech"),
    TEXTBOOK(35, "Textbook"),
    POETRY(36, "Poetry");

    private int code;
    private String description;

    public static BookGenre toEnum(Integer code){
        if(code == null){
            return null;
        }
        for(BookGenre bookGenre : BookGenre.values()){
            if(code.equals(bookGenre.getCode())){
                return bookGenre;
            }
        }
        throw new IllegalArgumentException("Invalid Id " + code + " supplied!");
    }
}
