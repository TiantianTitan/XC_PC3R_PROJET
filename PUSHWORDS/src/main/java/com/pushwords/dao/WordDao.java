package com.pushwords.dao;

import com.pushwords.po.Word;

import java.util.ArrayList;
import java.util.List;

public class WordDao {

    public int addOrUpdate(Word word) {
        String sql = "insert into tb_word (groupId,title,content,pubTime) values (?,?,?,now())";
        List<Object> params = new ArrayList<>();
        params.add(word.getGroupId());
        params.add(word.getTitle());
        params.add(word.getContent());

        return BaseDao.executeUpdate(sql, params);
    }

    public long findWordCount(Integer groupId) {
        String sql = "SELECT COUNT(1) FROM tb_word n where n.groupId = ?";
        List<Object> params = new ArrayList<>();
        params.add(groupId);
        return (long) BaseDao.findSingleValue(sql, params);
    }

    public List<Word> findWordListByPage(Integer groupId, Integer index, Integer pageSize) {
        String sql = "SELECT wordId, title, content, pubTime FROM tb_word n where n.groupId = ? LIMIT ?, ?";
        List<Object> params = new ArrayList<>();
        params.add(groupId);
        params.add(index);
        params.add(pageSize);

        return (List<Word>) BaseDao.queryRows(sql, params, Word.class);
    }
}

