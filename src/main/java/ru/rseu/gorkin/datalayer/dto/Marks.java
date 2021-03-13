package ru.rseu.gorkin.datalayer.dto;

public enum Marks {
    NOT_DEFINED(1), POSITIVE(3), NEGATIVE(2);

    private int id;
    private String title;

    Marks(int id) {
        this.id = id;
    }

    public static Marks getInstance(int id){
        for(Marks mark: Marks.values()){
            if (mark.id == id){
                return mark;
            }
        }
        throw new IllegalArgumentException();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
