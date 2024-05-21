package com.pushwords.service;

import cn.hutool.core.util.StrUtil;
import com.pushwords.dao.WordDao;
import com.pushwords.po.Word;
import com.pushwords.util.Page;
import com.pushwords.vo.ResultInfo;

import java.util.List;

public class WordService {

    private WordDao wordDao = new WordDao();


    public ResultInfo<Word> addOrUpdate(String groupId, String title, String content) {
        ResultInfo<Word> resultInfo = new ResultInfo<>();

        if(StrUtil.isBlank(groupId)){
            resultInfo.setCode(0);
            resultInfo.setMsg("Backend: choose the Group!");
            return resultInfo;
        }

        if(StrUtil.isBlank(title)){
            resultInfo.setCode(0);
            resultInfo.setMsg("Backend: enter a word!");
            return resultInfo;
        }

        if(StrUtil.isBlank(content)){
            resultInfo.setCode(0);
            resultInfo.setMsg("Backend: enter the description of the word!");
            return resultInfo;
        }

        Word word = new Word();
        word.setTitle(title);
        word.setContent(content);
        word.setGroupId(Integer.parseInt(groupId));

        resultInfo.setResult(word);

        int row = wordDao.addOrUpdate(word);
        if(row > 0){
            resultInfo.setCode(1);
        }else{
            resultInfo.setCode(0);
            resultInfo.setResult(word);
        }

        return  resultInfo;
    }

    public Page<Word> findWordListByPage(String pageNumStr, String pageSizeStr, String groupIdStr) {
        Integer pageNum = 1;
        Integer pageSize = 10;

        if(StrUtil.isBlank(groupIdStr)){
            return  null;
        }

        Integer groupId = Integer.parseInt(groupIdStr);

        if(!StrUtil.isBlank(pageNumStr)){
            pageNum = Integer.parseInt(pageNumStr);
        }
        if(!StrUtil.isBlank(pageSizeStr)){
            pageSize = Integer.parseInt(pageSizeStr);
        }

        long count = wordDao.findWordCount(groupId);
        if(count < 1){
            return  null;
        }
        Page<Word> page = new Page<>(pageNum,pageSize,count);

        Integer index = (pageNum-1)*pageSize;

        List<Word> wordList = wordDao.findWordListByPage(groupId,index,pageSize);

        page.setDataList(wordList);
        return page;
    }

}