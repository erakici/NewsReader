package com.intelygenz.esrarakici.newsreader.network;

import java.util.List;

public interface RequestListener {

     void onResponse(List value);
     void onLocalResponse(List value);
     void onError(String message);
}
