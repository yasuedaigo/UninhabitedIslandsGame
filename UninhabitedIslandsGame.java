import java.util.ArrayList;
import java.util.List;

public class UninhabitedIslandsGame {
    public static void main(String[] args) {
        List<EatItem> itemList = new ArrayList<EatItem>();
        itemList.add(new EatItem("毒蛇", 15, 30, "毒蛇の毒に負けた…"));
        itemList.add(new EatItem("漂流物（缶詰）", 30, 50, "歯では開けられなかった。歯が全部折れて出血死…"));
        itemList.add(new EatItem("流木", 8, 20, "バイキンだらけだった…"));
        itemList.add(new EatItem("落ち葉", 5, 20, "口の中の水分を全部持っていかれた…"));
        itemList.add(new EatItem("毒々しいキノコ", 10, 30, "笑いが止まらず疲れて死んだ…"));
        itemList.add(new EatItem("カラフルフルーツ", 5, 30, "種が喉に詰まった…"));
        Player player = new Player();
        while (isGameFinish()) {
            turnProcess(player);
        }
    }
    public static void turnProcess(Player player) {
        player.showInfo();
        choiceEatItem();
        player.choiceMove();
    }
}