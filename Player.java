import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;


public class Player {
    private static final int DEFAULT_HP = 50;
    private static final int MAX_HP = 100;
    private static final int MIN_HP = 0;
    private static final int MAX_HINT_COUNT = 3;
    private static final int DECREASE_HP = 10;
    private Scanner scanner;
    private int hp;
    private int hintCount = MAX_HINT_COUNT;

    /**
     * プレイヤーの行動の選択肢
     */
    public enum Move {
        EAT(1,"食べる"),
        DO_NOT_EAT(2,"食べない"),
        HINT(3,"ヒント");

        private final int value;
        private final String name;

        /**
         * @param value 行動を識別する数値
         * @param name 行動の名前
         */
        Move(int value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * 行動の選択肢を表示
         */
        private void showMoveValue(){
            System.out.println(this.value + " : " + this.name);
        }

        /**
         * @return 行動の名前
         */
        public String getName(){
            return this.name;
        }
    };

    private Move choisedMove;

    /**
     * Playerのコンストラクタ
     */
    public Player() {
        this.hp = DEFAULT_HP;
        scanner = new Scanner(System.in);
    }

    /**
     * プレイヤーのHPを表示
     */
    public void showInfo() {
        System.out.println("現在のプレイヤーのHP: " + this.hp);
    }

    /**
     * プレイヤーの行動を選択
     */
    public void choiceMove(){
        showChoices();
        choisedMove = null;
        while(choisedMove == null){
            determineMoveFromInputInt();
        }
    }

    /**
     * プレイヤーの行動を標準入力によって選択 ※ヒント抜き
     */
    private void choiceMoveWithoutHint(){
        showChoicesWithoutHint();
        choisedMove = null;
        while(choisedMove == null || choisedMove == Move.HINT){
            determineMoveFromInputInt();
        }
    }

    /**
     * プレイヤーの行動を標準入力によって選択
     */
    private void determineMoveFromInputInt(){
        int choiceInt = inputInt();
        for (Move move : Move.values()) {
            if (move.value == choiceInt) {
                choisedMove = move;
            }
        }
    }

    /**
     * 標準入力から数値を受け取る
     * @return 入力された数値
     */
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

    /**
     * プレイヤーの行動を実行
     * @param todayItem その日のアイテム
     * @param nextItem 次の日のアイテム
     */
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

    /**
     * 行動の選択選択肢を表示
     */
    private void showChoices(){
        for (Move move : Move.values()) {
            move.showMoveValue();
        }
    }

    /**
     * 行動の選択選択肢を表示 ※ヒント抜き
     */
    private void showChoicesWithoutHint(){
        for (Move move : Move.values()) {
            if(move != Move.HINT){
                move.showMoveValue();
            }
        }
    }

    /**
     * @return プレイヤーのHP
     */
    public int getHp() {
        return hp;
    }

    /**
     * @return プレイヤーの最小HP
     */
    public int get_MIN_HP() {
        return MIN_HP;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * @return プレイヤーに設定されている行動
     */
    public Move getMove() {
        return choisedMove;
    }

    /**
     * アイテムを食べる行動
     * @param item 食べるアイテム
     */
    private void eat(EatItem item)
    {
        //食べることができた場合
        if (item.canEat()) {
            this.hp += item.getExpectedHeelingHP();
            if(this.hp > Player.MAX_HP){
                this.hp = Player.MAX_HP;
            }
            System.out.println(item.getItemName() + " を食べた。HPが回復した!");
        } else {
        //食べることができなかった場合
            System.out.println(item.getItemName() + " を食べられなかった。");
            System.out.println(item.getCauseOfDeath());
            this.hp = Player.MIN_HP;
        }
    }

    /**
     * アイテムを食べない行動
     * @param item 食べないアイテム
     */
    private void doNotEat(EatItem item)
    {
        System.out.println(item.getItemName() + "を食べなかった");
        this.hp -= DECREASE_HP;
    }

    /**
     * ヒントを見る行動
     * @param item 次の日のアイテム
     */
    private void hint(EatItem item)
    {
        if(hintCount <= 0){
            System.out.println("ヒントは3回しか使えません。");
            return;
        }
        hintCount--;
        System.out.println("次の日のアイテムは " + item.getItemName() + " です。");
        System.out.println("残りのヒントは " + hintCount + " 回です。");
    }
}
