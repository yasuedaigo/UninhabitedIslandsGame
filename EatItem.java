import java.util.Random;


public class EatItem {
    private static final int MAX_RANDOM_RANGE = 100;
    private String itemName; // アイテム名
    private int dangerLevel; // 危険度
    private int expectedHeelingHp; // 食べたときのHP回復量
    private String causeOfDeath; // 死因

    /**
     * EatItemのコンストラクタ
     * @param itemName アイテム名
     * @param dangerLevel 危険度
     * @param expectedHeelingHp 食べたときのHP回復量
     * @param causeOfDeath 死因
     */
    public EatItem(
          String itemName, int dangerLevel, int expectedHeelingHp, String causeOfDeath
        ) {
        this.itemName = itemName;
        this.dangerLevel = dangerLevel;
        this.expectedHeelingHp = expectedHeelingHp;
        this.causeOfDeath = causeOfDeath;
    }

    /** 
     * @return itemName アイテム名
     */
    public String getItemName() {
        return itemName;
    }

    /** 
     * @return dangerLevel 危険度
     */
    public int getDangerLevel() {
        return dangerLevel;
    }

    /** 
     * @return expectedHeelingHp 食べたときのHP回復量
     */
    public int getExpectedHeelingHP() {
        return expectedHeelingHp;   
    }

    /** 
     * @return causeOfDeath 死因
     */
    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    /**
     * アイテムの情報を表示
     */
    public void showInfo(){
        System.out.println(itemName + " を見つけました。");
        System.out.println("危険度 : " + dangerLevel);
        System.out.println("回復量 : " + expectedHeelingHp);
    }

    /**
     * 食べることができたかどうかの判定
     * dangerLevelと乱数の値で判定を行う
     * @return true:食べることができた / false:食べることができなかった
     */
    public boolean canEat() {
        Random random = new Random();
        return random.nextInt(MAX_RANDOM_RANGE) >= dangerLevel;
    }
}