package com.mhc.word_splite;

import com.alibaba.fastjson.JSON;
import com.mhc.word_splite.pojo.WordCountDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.analysis.HotWord;
import org.apdplat.word.segmentation.Word;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-05-22 13:19
 */
public class WordSeg {

  private static final String REPORT_2020 = ClassLoader.getSystemClassLoader().getResource("sourcedata/2020.txt").getFile();
  private static final String REPORT_2019 = ClassLoader.getSystemClassLoader().getResource("sourcedata/2019.txt").getFile();
  private static final String REPORT_2018 = ClassLoader.getSystemClassLoader().getResource("sourcedata/2018.txt").getFile();
  private static final String REPORT_2017 = ClassLoader.getSystemClassLoader().getResource("sourcedata/2017.txt").getFile();
  private static final String REPORT_2016 = ClassLoader.getSystemClassLoader().getResource("sourcedata/2016.txt").getFile();
  private static final Long REPORT_WORD_LIMIT = 200L;


  public static void main(String[] args) throws Exception {
    Set<String> wordSet = new HashSet<>();
    HashMap<String, List<String>> wordMap = new HashMap<>();
    HashMap<String, List<String>> newWordMap = new HashMap<>();
    List<WordCountDTO> wordCountDTO_2016 = anaylizeReportByWord(REPORT_2016, REPORT_WORD_LIMIT);
    wordMap.put("2016",wordCountDTO_2016.stream().map(WordCountDTO::getText).collect(Collectors.toList()));
    newWordMap.put("2016",processKeyWord(wordSet,wordCountDTO_2016));
    List<WordCountDTO> wordCountDTO_2017 = anaylizeReportByWord(REPORT_2017, REPORT_WORD_LIMIT);
    wordMap.put("2017",wordCountDTO_2017.stream().map(WordCountDTO::getText).collect(Collectors.toList()));
    newWordMap.put("2017",processKeyWord(wordSet,wordCountDTO_2017));
    List<WordCountDTO> wordCountDTO_2018 = anaylizeReportByWord(REPORT_2018, REPORT_WORD_LIMIT);
    wordMap.put("2018",wordCountDTO_2018.stream().map(WordCountDTO::getText).collect(Collectors.toList()));
    newWordMap.put("2018",processKeyWord(wordSet,wordCountDTO_2018));
    List<WordCountDTO> wordCountDTO_2019 = anaylizeReportByWord(REPORT_2019, REPORT_WORD_LIMIT);
    wordMap.put("2019",wordCountDTO_2019.stream().map(WordCountDTO::getText).collect(Collectors.toList()));
    newWordMap.put("2019",processKeyWord(wordSet,wordCountDTO_2019));
    List<WordCountDTO> wordCountDTO_2020 = anaylizeReportByWord(REPORT_2020, REPORT_WORD_LIMIT);
    wordMap.put("2020",wordCountDTO_2020.stream().map(WordCountDTO::getText).collect(Collectors.toList()));
    newWordMap.put("2020",processKeyWord(wordSet,wordCountDTO_2020));
    TimeUnit.SECONDS.sleep(5);
    System.out.println("-------------->\r\n"+JSON.toJSONString(wordSet));
    System.out.println("-------------->\r\n"+JSON.toJSONString(wordMap));
    System.out.println("-------------->\r\n"+JSON.toJSONString(newWordMap));

  }

  private static List<String> processKeyWord(Set<String> wordSet, List<WordCountDTO> wordCountDTO) {
    List<String> newWords = new ArrayList<>();
    if (CollectionUtils.isEmpty(wordSet)){
      for (WordCountDTO countDTO : wordCountDTO) {
        wordSet.add(countDTO.getText());
      }
    }else {
      for (WordCountDTO countDTO : wordCountDTO) {
        if (wordSet.add(countDTO.getText())){
          newWords.add(countDTO.getText());
        }
      }
    }
    return newWords;
  }

  private static List<WordCountDTO> anaylizeReportByWord(String path, Long limit) throws Exception {
    String data = FileUtils.readFileToString(new File(path));
    List<Word> wordList = WordSegmenter.seg(data);
    List<WordCountDTO> wordCountDTOS = wordList.stream()
        .collect(Collectors.groupingBy(Word::getText))
        .entrySet().stream()
        .map(entry -> {
          WordCountDTO wordCountDTO = new WordCountDTO();
          wordCountDTO.setText(entry.getKey());
          wordCountDTO.setCount(Long.valueOf(entry.getValue().size()));
          return wordCountDTO;
        }).sorted(Comparator.comparingLong(WordCountDTO::getCount).reversed()).limit(limit)
        .collect(Collectors.toList());
    return wordCountDTOS;
  }

}
