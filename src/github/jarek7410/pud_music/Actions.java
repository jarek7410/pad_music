package github.jarek7410.pud_music;

public enum Actions {
    //NULL("NULL"),
    CLOSE,
    STOP,
    CHANGE,
    ONE,
    TWO,
    THREE,
    FOUR;


    private Actions(){
    }
    public static boolean isCorrect(String s){
        Actions[] a = Actions.values();
        for (Actions aa : a) {
            if(aa.name().equals(s))return true;
        }
        return false;
    }
}
