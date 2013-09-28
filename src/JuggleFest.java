import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Main main. assign Juggle to Circuit
 * @author xiaonanwang
 * 
 */
public class JuggleFest {
  // store all the circuit team
  ArrayList<Circuit> cgroup = new ArrayList<Circuit>();
  int groupSize = 6;
  public void assign() throws IOException {
    // read file to init circuit and juggle group
    Scanner scanner = new Scanner(new FileReader("jugglefest.txt"));
    
    // use PriorityQueue to Store the Circuit group, it will sort according to comparator
    Comparator<Juggle> comparator = new JuggleComparator();
    ArrayList<PriorityQueue<Juggle>> allQ = new ArrayList<PriorityQueue<Juggle>>();
    
    // store the Juggle whose queue is empty, which means can be assigned to any circuit
    ArrayList<Juggle> left = new ArrayList<Juggle>();
    
    // init circuit group
    while (scanner.hasNextLine()) {
      String s = scanner.nextLine();
      //circuit group down
      if (s.length() == 0 || s.charAt(0) != 'C') {
        break;
      }
      Circuit c = new Circuit(s);
      cgroup.add(c);
      PriorityQueue<Juggle> queue = new PriorityQueue<Juggle>(6, comparator);
      allQ.add(queue);
    }
    
    //init juggle group
    while (scanner.hasNextLine()) {
      Juggle j = new Juggle(scanner.nextLine());
      // set up juggle's info
      for(Iterator<Integer> it = j.queue.iterator();it.hasNext();){
        j.buildInfo(cgroup.get(it.next()));
      }
      while (true) {
        int bestC;
        if (j.queue.size() != 0) {
          bestC = j.queue.peek();
        }
        // the queue is empty. it is free to assign any circuit. deal with it later
        else {
          left.add(j);
          break;
        }
        // the circuit is not full. add directly
        if (allQ.get(bestC).size() < groupSize) {
          allQ.get(bestC).add(j);
          break;
        }
        // the circuit is full, compare j with tmp(smallest in this circuit) 
        else {
          Juggle tmp = allQ.get(bestC).peek();
          int group = tmp.queue.peek();
          Circuit c = cgroup.get(group);
          int mark = c.H * tmp.H + c.E * tmp.E + c.P * tmp.P - 
              (c.H * j.H + c.E * j.E + c.P * j.P);
          if (mark >= 0) {
            j.queue.poll();
          } else {
            tmp.queue.poll();
            allQ.get(bestC).poll();
            allQ.get(bestC).add(j);
            j = tmp;
          }
        }
      }
    }
    scanner.close();
    //assign the left juggle to non-full circuit queue
    int j = 0;
    for (int i = 0; i < allQ.size(); i++) {
      if(allQ.get(i).size() == groupSize){
        continue;
      }
      while (allQ.get(i).size() < groupSize) {
        allQ.get(i).add(left.get(j));
        j++;
      }
    }

    //calculate the 1970 line
    Iterator<Juggle> it = allQ.get(1970).iterator();
    int result = 0;
    while (it.hasNext()) {
      result += it.next().ID;
    }
    System.out.println(result);
    
    // write to file
    BufferedWriter writer = new BufferedWriter(new FileWriter(new File("output.txt")));
    for(int i = 0; i < allQ.size(); i++){
      StringBuilder start = new StringBuilder();
      while(allQ.get(i).size() > 0){
        Juggle juggle = allQ.get(i).poll();
        if(allQ.get(i).size() == groupSize - 1){
          start.insert(0,"J" + juggle.ID + juggle.info);
        } else {
          start.insert(0,"J" + juggle.ID + juggle.info + ",");
        }
      }
      start.insert(0, "C" + i + " ");
      writer.write(start.toString());
      writer.newLine();
    }
    writer.close();
  }
  
  /**
   * main method
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    JuggleFest fest = new JuggleFest();
    fest.assign();
  }
  
  /**
   * sort the circuit team member according to the H E P and circuit ID
   * for priority queue
   * @author xiaonanwang
   * 
   */
  public class JuggleComparator implements Comparator<Juggle> {
    /**
     * overwrite the compare method
     */
    public int compare(Juggle o1, Juggle o2) {
      if(o1.queue.size() == 0){
        return  -1;
      }
      int group = o1.queue.peek();

      Circuit c = cgroup.get(group);
      int mark = c.H * o1.H + c.E * o1.E + c.P * o1.P - (c.H * o2.H + c.E * o2.E + c.P * o2.P);
      if (mark > 0) {
        return 1;
      } else if (mark < 0) {
        return -1;
      } else {
        return 0;
      }
    }
  }
}
