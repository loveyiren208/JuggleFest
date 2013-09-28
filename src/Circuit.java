/**
 * This is the circuit class.
 * it stores the H E P
 * @author xiaonanwang
 *
 */
public class Circuit {
  int ID;
  int H;
  int E;
  int P;
  /**
   * read one line String. parse it into different values
   * the format should be "C C0 H:7 E:7 P:10"
   * @param s
   */
  public Circuit (String s){
    String[] group = s.split(" ");
    this.ID = Integer.parseInt(group[1].replace("C",""));
    this.H = Integer.parseInt(group[2].replace("H:",""));
    this.E = Integer.parseInt(group[3].replace("E:",""));
    this.P = Integer.parseInt(group[4].replace("P:",""));
  }
  @Override
  public String toString() {
    return this.ID+ " " + this.H + this.E+this.P;
  }
}
