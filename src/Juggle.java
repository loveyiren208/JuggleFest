import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

/**
 * This is Juggle class
 * it stores H E P and a queue, which stores circuit number according 
 * to preference
 * @author xiaonanwang
 *
 */
public class Juggle {
  int ID;
  int H;
  int E;
  int P;
  Queue<Integer> queue;
  String info;
  /**
   * read one line String. parse it into different values
   * the format should be "J J0 H:3 E:9 P:2 C2,C0,C1"
   * @param s
   */
  public Juggle (String s) {
    String[] group = s.split(" ");
    this.ID = Integer.parseInt(group[1].replace("J",""));
    this.H = Integer.parseInt(group[2].replace("H:",""));
    this.E = Integer.parseInt(group[3].replace("E:",""));
    this.P = Integer.parseInt(group[4].replace("P:",""));
    String[] queueGroup = group[5].split(",");
    queue = new ArrayDeque<Integer>();
    for (int i = 0; i < queueGroup.length;i++ ){
      queue.add(Integer.parseInt(queueGroup[i].replace("C","")));
    }
    this.info = "";
  }
  
  // set up Juggle's info. the scores of prefer circuits
  public void buildInfo(Circuit c){
    this.info += " C"+ c.ID + ":" + ( c.H * this.H +c.E * this.E + c.P * this.P );
  }

  @Override
  public String toString() {
    String s = this.ID + "H:" + this.H +"E:" + this.E  + "P:" + this.P;
    Iterator<Integer> it = this.queue.iterator();
    while(it.hasNext()){
      s = s + " " + it.next();
    }
    return s;
  }

}
