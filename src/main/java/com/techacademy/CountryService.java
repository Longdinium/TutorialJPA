package com.techacademy;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 このクラスでJpaRepositoryに用意されている以下のメソッドを呼び出して使用するメソッドを定義する
 findAll(): 全件検索
 findById(): 1件検索（主キーによる検索）
 save(): 作成、更新
 deleteById(): 1件削除（主キーによる削除）
  */

@Service
/*
 このアノテーションによりサービスクラスであることを示している
 @ServiceをつけたクラスはSpring起動時にDIコンテナ―へ登録される→ここよく分からない
 */
public class CountryService {
    private final CountryRepository repository;
    /*
     Spring Frameworkの管理下に置くクラスを「コンポーネント」と呼ぶが、ここではそれを利用
     private finalで変数を定義？
     →リポジトリから引数付きコンストラクタを用意、DIコンテナーを経由してインスタンスを取得
     ここではrepository変数でコンポーネントの使用を定義
     */

    @Autowired
    public CountryService(CountryRepository repository) {
        this.repository = repository;
    }

    // 全件を検索して返す
    public List<Country> getCountryList() {
        // リポジトリのfindAllメソッドを呼び出す
        return repository.findAll();
    }

    // ----- 追加：ここから（Lesson16 Chapter5.1） -----

    // （特定の？）1件を（Codeをキーに）検索して返す
    public Country getCountry(String code) {
        // findById検索するメソッドを引っ張ってくる
        Optional<Country> option = repository.findById(code);
        // 取得できなかった場合はnullを返す
        Country country = option.orElse(null); // ここよく分からん
        return country;
    }
        /*
         OptionalはJavaの構文？　そういうものだと思っておく
         ->どうやらifブロックを使う必要のあったnullチェックを簡潔にすることができる
         （例）
         Optional<T> オブジェクト名 = Optional.ofNullable(nullの可能性があるオブジェクト名);

         ここでは、orElse(null) で取得できなかった場合に null を返す

         参考 https://www.sejuku.net/blog/60892
         */


    // 更新（追加）を行う
    @Transactional
    // @Transactionalアノテーションは該当クラスのすべてのメソッドをトランザクション管理対象に設定します
    // なおこのアノテーションをつけたメソッドはオートコミットがOFFとなり、コミットはメソッドが終わる際に自動で行われる
    public void updateCountry(String code, String name, int population) {
        Country country = new Country(code, name, population);
        repository.save(country);
    }

    // 削除を行う
    @Transactional
    public void deleteCountry(String code) {
        repository.deleteById(code);
    }
 // ----- 追加：ここまで（Lesson16 Chapter5.1） -----

}