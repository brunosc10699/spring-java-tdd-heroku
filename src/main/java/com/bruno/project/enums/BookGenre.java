package com.bruno.project.enums;

public enum BookGenre {

    ACTION(1, "Action"),
    ADVENTURE(2, "Adventure"),
    ANTHOLOGY(3, "Anthology"),
    CLASSIC(4, "Classic"),
    COMICANDGRAPHICNOVEL(5, "Comic and Graphic Novel"),
    CRIMEANDDETECTIVE(6, "Crime and Detective"),
    DRAMA(7, "Drama"),
    FABLE(8, "Fable"),
    FAIRYTALE(9, "Fairy Tale"),
    FANTASY(10, "Fantasy"),
    HORROR(11, "Horror"),
    HUMOR(12, "Humor"),
    LEGEND(13, "Legend"),
    MAGICALREALISM(14, "Magical Realism"),
    MYSTERY(15, "Mystery"),
    MYTHOLOGY(16, "Mythology"),
    FANFICTION(17, "Fan Fiction"),
    HISTORICALFICTION(18, "Historical Fiction"),
    REALISTICFICTION(19, "Realistic Fiction"),
    SCIENCEFICTION(20, "Science Fiction"),
    ROMANCE(21, "Romance"),
    SATIRE(22, "Satire"),
    SCIFI(23, "Sci-Fi"),
    SHORTSTORY(24, "Short Story"),
    SUSPENSE(25, "Suspense"),
    THRILLER(26, "Thriller"),
    BIOGRAPHY(27, "Biography"),
    AUTOBIOGRAPHY(28, "Autobiography"),
    ESSAY(29, "Essay"),
    MEMOIR(30, "Memoir"),
    NARRATIVENONFICTION(31, "Narrative Non-Fiction"),
    PERIODICALS(32, "Periodicals"),
    REFERENCEBOOKS(33, "Reference Books"),
    SELFHELPBOOK(34, "Self-Help Book"),
    SPEECH(35, "Speech"),
    TEXTBOOK(36, "Textbook"),
    POETRY(37, "Poetry");

    private int code;
    private String description;

    BookGenre(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
