import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameManager {
    public static List<Player.Move> moveLogList = new ArrayList<>();
    public static List<Integer> hpLogList = new ArrayList<>();
    public static  List<EatItem> itemLogList = new ArrayList<>();
    private final int MAX_TURN_COUNT = 10;
    private int turnCount;
    private List<EatItem> itemList;
    private Player player;
    private Random random;
    private EatItem todayItem;
    private EatItem nextItem;

    /**
     * GammeManagerのコンストラクタ
     */
    public GameManager() {
        player = new Player();
        random = new Random();
        itemList = new ArrayList<>();
        itemList.add(new EatItem("毒蛇", 15, 30, "毒蛇の毒に負けた…"));
        itemList.add(new EatItem("漂流物（缶詰）", 30, 50, "歯では開けられなかった。歯が全部折れて出血死…"));
        itemList.add(new EatItem("流木", 8, 20, "バイキンだらけだった…"));
        itemList.add(new EatItem("落ち葉", 5, 20, "口の中の水分を全部持っていかれた…"));
        itemList.add(new EatItem("毒々しいキノコ", 10, 30, "笑いが止まらず疲れて死んだ…"));
        itemList.add(new EatItem("カラフルフルーツ", 5, 30, "種が喉に詰まった…"));
    }

    /**
     * ゲームをスタートする処理
     */
    public void startGame() {
        turnCount = 1;
        while (!isGameFinish()) {
            turnProcess();
            turnCount++;
        }
        System.out.println("30日間生き延びることができた！");
        showLog(player);
    }

    /**
     * ターンの処理
     */
    public void turnProcess(){
        System.out.println("");
        System.out.println(turnCount + " 日目");
        hpLogList.add(player.getHp());
        player.showInfo();
        determineTodayItem();
        determineNextItem();
        todayItem.showInfo();
        player.choiceMove();
        player.doMove(todayItem, nextItem);
        itemLogList.add(todayItem);
    }

    /**
     * プレイヤーの行動を記録
     * @param move プレイヤーの行動
     */
    public void addMoveLog(Player.Move move){
        moveLogList.add(move);
    }

    /**
     * プレイヤーのHPを記録
     * @param hp プレイヤーのHP
     */
    public void addHpLog(int hp){
        hpLogList.add(hp);
    }

    /**
     * 今日のアイテムを決定
     */ 
    private void determineTodayItem() {
        // nextItemが決まっていればそれを今日のアイテムにしてnextItemをリセット
        if(nextItem != null){
            todayItem = nextItem;
            nextItem = null;
            return;
        }
        todayItem = getRandomItem();
    }

    /**
     * 次の日のアイテムを決定
     */ 
    private void determineNextItem(){
        nextItem = getRandomItem();
    }

    /**
     * アイテムリストからランダムにアイテムを取得
     * @return ランダムに取得したアイテム
     */ 
    private EatItem getRandomItem() {
        return itemList.get(random.nextInt(itemList.size()));
    }

    /**
     * ゲームが終了しているか判定
     * @return true:終了する / false:終了しない
     */
    private boolean isGameFinish() {
        if(player.getHp() <= player.get_MIN_HP()){
            return true;
        }
        if(turnCount >= MAX_TURN_COUNT){
            return true;
        }
        return false;
    }

    /** 
     * ゲームのログを表示
     * @param player プレイヤー
     */ 
    private void showLog(Player player) {
        System.out.println("------プレイ記録------");
        for(int i = 0; i < turnCount; i++){
            System.out.print((turnCount) + "日目  ");
            System.out.print("HP:" + hpLogList.get(i) + "  ");
            System.out.print("食料:" + itemLogList.get(i).getItemName() + "  ");
            System.out.println("行動:" + moveLogList.get(i).getName() + "  ");
        }
    }
}
