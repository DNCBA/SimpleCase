package com.mhc.java8;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019-10-31 20:09
 */
public class StreamTest {


  public static void main(String[] args) {
    //构造数据
    List<Ticket> list = initTickets();

    List<Temp> total = new ArrayList<Temp>();

    //数据处理
    List<Temp> resultA = processTicket(list);
    List<Temp> resultB = processTicket(list);
    List<Temp> resultC = processTicket(list);
    total.addAll(resultA);
    total.addAll(resultB);
    total.addAll(resultC);

    HashMap<Integer, Temp> hashMap = new HashMap<>();
    for (Temp temp : total) {
      if (null == hashMap.get(temp.getId())) {
        hashMap.put(temp.getId(), temp);
      } else {
        Temp temp1 = hashMap.get(temp.getId());
        temp1.setTotalFlag(temp1.getTotalFlag() + temp.getTotalFlag());
        temp1.setTrueFlag(temp1.getTrueFlag() + temp.getTrueFlag());
        temp1.setFalseFlag(temp1.getFalseFlag() + temp.getFalseFlag());
        hashMap.put(temp1.getId(), temp1);
      }
    }

    Map<String, List<Temp>> collect = hashMap.values().stream()
        .collect(Collectors.groupingBy(temp -> {
          if (temp.getId() > 7) {
            return "0-7";
          } else {
            return "8-0";
          }
        }));

    System.out.println(collect);



  }

  private static List<Temp> processTicket(List<Ticket> list) {

    List<Temp> collect = list.stream()
        .collect(Collectors.groupingBy(Ticket::getId)).entrySet().stream().map(e -> {
          Temp temp = new Temp();
          temp.setId(e.getKey());
          temp.setTrueFlag(e.getValue().stream().filter(Ticket::getActive).count());
          temp.setTotalFlag(Long.valueOf(e.getValue().size()));
          temp.setFalseFlag(temp.getTotalFlag() - temp.getTrueFlag());
          return temp;
        }).collect(Collectors.toList());
    return collect;


  }

  private static List<Ticket> initTickets() {
    ArrayList<Ticket> tickets = new ArrayList<>();
    tickets.add(new Ticket(1, true, "a"));
    tickets.add(new Ticket(1, true, "b"));
    tickets.add(new Ticket(1, true, "c"));
    tickets.add(new Ticket(1, true, "d"));
    tickets.add(new Ticket(1, true, "e"));
    tickets.add(new Ticket(1, false, "f"));

    tickets.add(new Ticket(2, true, "g"));
    tickets.add(new Ticket(2, false, "h"));
    tickets.add(new Ticket(2, true, "m"));

    tickets.add(new Ticket(3, false, "k"));
    tickets.add(new Ticket(3, false, "d"));
    tickets.add(new Ticket(3, true, "w"));
    tickets.add(new Ticket(3, false, "k"));
    tickets.add(new Ticket(3, false, "o"));
    return tickets;
  }


}
