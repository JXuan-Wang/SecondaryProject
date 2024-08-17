package org.example.systemserver.service;

import com.github.pagehelper.PageInfo;
import org.example.systemserver.domain.TClue;
import org.example.systemserver.query.ClueQuery;

import java.io.InputStream;

public interface ClueService {
    PageInfo<TClue> getClueByPage(Integer current);

    void importExcel(InputStream inputStream, String token);

    Boolean checkPhone(String phone);

    int saveClue(ClueQuery clueQuery);

    TClue getClueById(Integer id);

    int updateClue(ClueQuery clueQuery);
}
