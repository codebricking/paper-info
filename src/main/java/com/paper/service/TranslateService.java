package com.paper.service;


import com.paper.model.entity.PaperInfo;

import java.util.LinkedList;

public interface TranslateService {


    LinkedList<PaperInfo> translate(int maxNum);

    String deepLTranslate(String text) throws Exception;

}
