import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
public class Player {
    private static final int DEFAULT_HP = 50;
    private static final int MAX_HP = 100;
    private static final int MIN_HP = 0;
    private static final int MAX_HINT_COUNT = 3;
    private static final int DECREASE_HP = 10;
    private static final String SHOWINFO_MESSAGE = "現在のプレイヤーのHP: %d";  // メッセージテンプレート
    Scanner scanner;
    private int hp; // ヒットポイント
    private int hintCount = MAX_HINT_COUNT; // 選択肢
    public enum Move {
        EAT(1,"食べる"),
        DO_NOT_EAT(2,"食べない"),
        HINT(3,"ヒント");

        private final int value;
        private final String name;
        Move(int value, String name) {
            this.value = value;
            this.name = name;
        }
        private void showMovevalue(){
            System.out.println(this.value + " : " + this.name);
        }
        public String getName(){
            return this.name;
        }
    };
    private Move choisedMove;

    public Player() {
        this.hp = DEFAULT_HP;  // 初期HPを50に設定
        scanner = new Scanner(System.in);
    }

    public void showInfo() {
        System.out.println(String.format(SHOWINFO_MESSAGE, hp));
    }

    public void choiceMove(){
        showChoices();
        choisedMove = null;
        while(choisedMove == null){
            determineMoveFromInputInt();
        }
    }

    public void choiceMoveWithoutHint(){
        showChoicesWithoutHint();
        choisedMove = null;
        while(choisedMove == null || choisedMove == Move.HINT){
            determineMoveFromInputInt();
        }
    }

    public void determineMoveFromInputInt(){
        int choiceInt = inputInt();
        for (Move move : Move.values()) {
            if (move.value == choiceInt) {
                choisedMove = move;
            }
        }
    }

    private int inputInt(){
        int inputInt = -1;
        while (true) {
            try {
                System.out.println("選択肢から数字を入力してください。");
                inputInt = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("無効な入力です。");
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("入力が中断されました。");
                break;
            }
        }
        return inputInt;
    }

    public void doMove(EatItem todayItem,EatItem nextItem){
        switch (choisedMove) {
            case EAT:
                eat(todayItem);
                GameManager.moveLogList.add(choisedMove);
                break;
            case DO_NOT_EAT:
                doNotEat(todayItem);
                GameManager.moveLogList.add(choisedMove);
                break;
            case HINT:
                hint(nextItem);
                this.choiceMoveWithoutHint();
                this.doMove(todayItem,nextItem);
                break;
        }
        
    }

    public void showChoices(){
        for (Move move : Move.values()) {
            move.showMovevalue();
        }
    }

    public void showChoicesWithoutHint(){
        for (Move move : Move.values()) {
            if(move != Move.HINT){
                move.showMovevalue();
            }
        }
    }

    public int getHp() {
        return hp;
    }

    public int get_MIN_HP() {
        return MIN_HP;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Move getMove() {
        return choisedMove;
    }

    public void eat(EatItem item)
    {
        if (item.canEat()) {
            this.hp += item.getExpectedHeelingHP();
            if(this.hp > Player.MAX_HP){
                this.hp = Player.MAX_HP;
            }
            System.out.println(item.getItemName() + " を食べた。HPが回復した!");
        } else {
            // 食べられない場合の効果を適用
            System.out.println(item.getItemName() + " を食べられなかった。");
            System.out.println(item.getCauseOfDeath());
            this.hp = Player.MIN_HP;
        }
    }

    public void doNotEat(EatItem item)
    {
        System.out.println(item.getItemName() + "を食べなかった");
        this.hp -= DECREASE_HP;
    }

    public void hint(EatItem item)
    {
        if(hintCount <= 0){
            System.out.println("ヒントは3回しか使えません。");
            return;
        }else{
            hintCount--;
        }
        System.out.println("次の日のアイテムは " + item.getItemName() + " です。");
        System.out.println("残りのヒントは " + hintCount + " 回です。");
    }
}
