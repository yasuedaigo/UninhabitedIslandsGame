import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {
    public static List<Player.Move> moveLogList;
    public static List<Integer> hpLogList;
    private final int MAX_TURN_COUNT = 10;
    private int turnCount = 0;
    private List<EatItem> itemList;
    private List<EatItem> itemLogList;
    private Player player;
    private Random random;
    private EatItem todayItem;
    private EatItem nextItem;
    public GameManager() {
        player = new Player();
        random = new Random();
        itemList = new ArrayList<>();
        itemLogList = new ArrayList<>();
        hpLogList = new ArrayList<>();
        moveLogList = new ArrayList<>();
        itemList.add(new EatItem("毒蛇", 15, 30, "毒蛇の毒に負けた…"));
        itemList.add(new EatItem("漂流物（缶詰）", 30, 50, "歯では開けられなかった。歯が全部折れて出血死…"));
        itemList.add(new EatItem("流木", 8, 20, "バイキンだらけだった…"));
        itemList.add(new EatItem("落ち葉", 5, 20, "口の中の水分を全部持っていかれた…"));
        itemList.add(new EatItem("毒々しいキノコ", 10, 30, "笑いが止まらず疲れて死んだ…"));
        itemList.add(new EatItem("カラフルフルーツ", 5, 30, "種が喉に詰まった…"));
    }

    public void startGame() {
        while (!isGameFinish()) {
            turnProcess();
            turnCount++;
        }
        showLog(player);
    }

    public void turnProcess(){
        hpLogList.add(player.getHp());
        System.out.println("");
        System.out.println(turnCount + 1 + " 日目");
        player.showInfo();
        determineTodayItem();
        nextItem = choiseItem();
        todayItem.showInfo();
        player.choiceMove();
        player.doMove(todayItem, nextItem);
        
        itemLogList.add(todayItem);
    }

    private void determineTodayItem() {
        if(nextItem != null){
            todayItem = nextItem;
            nextItem = null;
            return;
        }
        todayItem = choiseItem();
    }

    private EatItem choiseItem() {
        return itemList.get(random.nextInt(itemList.size()));
    }

    private boolean isGameFinish() {
        if(player.getHp() <= player.get_MIN_HP()){
            return true;
        }
        if(turnCount >= MAX_TURN_COUNT){
            System.out.println("30日間生き延びることができた！");
            return true;
        }
        return false;
    }

    private void showLog(Player player) {
        System.out.println("------プレイ記録------");
        for(int i = 0; i < turnCount; i++){
            System.out.print((i + 1) + "日目  ");
            System.out.print("HP:" + hpLogList.get(i) + "  ");
            System.out.print("食料:" + itemLogList.get(i).getItemName() + "  ");
            System.out.println("行動:" + moveLogList.get(i).getName() + "  ");
        }
    }
}
