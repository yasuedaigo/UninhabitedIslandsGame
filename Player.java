import java.util.Scanner;
public class Player {
    private static final int DEFAULT_HP = 100;
    private static final int MIN_HP = 0;
    private static final String SHOWINFO_MESSAGE = "現在のプレイヤーのHP: %d";  // メッセージテンプレート
    private enum Move {
        EAT(1),
        DO_NOT_EAT(2),
        HINT(3);

        private final int value;
        Move(int value) {
            this.value = value;
        }

        private static Move fromInt(int value) {
            for (Move move : Move.values()) {
                if (move.value == value) {
                    return move;
                }
            }
            return null;
        }
    };
    private Move move;
    private int hp; // ヒットポイント

    public Player() {
        this.hp = DEFAULT_HP;  // 初期HPを100に設定
    }

    public void showInfo() {
        System.out.println(String.format(SHOWINFO_MESSAGE, hp));
    }

    public void eat(EatItem item) {
        if (item.canEat()) {
            this.hp += item.getExpectedHeelingHP();
            System.out.println(item.getItemName() + "を食べた。HPが回復した！");
        } else {
            // 食べられない場合の効果を適用
            System.out.println(item.getItemName() + "を食べられなかった。");
            System.out.println(item.getCauseOfDeath());
            this.hp = MIN_HP;
        }
    }

    public void doNotEat(EatItem item) {
        System.out.println(item.getItemName() + "を食べなかった");
    }

    public void choiceMove(){
        move = null;
        showChoices();
        while(move == null){
            Scanner scanner = new Scanner(System.in);
            int choiceInt = scanner.nextInt();
            move = Move.fromInt(choiceInt);
        }

    }

    public void doMove(Move move, EatItem item){
        switch(move){
            case EAT:
                eat(item);
                break;
            case DO_NOT_EAT:
                doNotEat(item);
                break;
            case HINT:
                hint();
                break;
        }
    }

    public void showChoices(){
        System.out.println("どうしますか？");
        for (Move move : Move.values()) {
            System.out.println(move.value + ":" + move.name());
        }
    }

    public void hint(){
        System.out.println("ヒント");
        choiceMove();
    }
}
