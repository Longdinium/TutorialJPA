package com.techacademy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("country")
public class CountryController {
    private final CountryService service;

    public CountryController(CountryService service) {
        this.service = service;
    }

    // ----- 一覧画面 -----
    @GetMapping("/list")
    public String getList(Model model) {
        // 全件検索結果をModelに登録
        model.addAttribute( "countrylist", service.getCountryList() );
        // country/list.htmlに画面遷移
        return "country/list";
    }


    // ----- 追加:ここから Lesson16 Chapter5.2 -----
    // ----- 詳細画面 -----
    @GetMapping(value = { "/detail", "/detail/{code}/" } )
    public String getCountry(@PathVariable(name = "code", required = false) String code, Model model) {
        Country country = code != null ? service.getCountry(code) : new Country();
        // （URLで？）codeが指定されていたら検索結果を、無ければ空のクラスを設定 -> つまり三項演算子
        // 必ず Country オブジェクトをModelに登録することで、detail.html テンプレートの方でエラーになることを防いでいます。

        // Modelに登録
        model.addAttribute("country", country);
        // country/detail.htmlに画面遷移
        return "country/detail";
    }

    // ----- 更新（追加）　-----
    @PostMapping("/detail")
    public String postCountry(@RequestParam("code") String code, @RequestParam("name") String name, @RequestParam("population") int population, Model model) {
        /*
        画面のformで入力した値をパラメータとして受け取り、サービスの updateCountry() メソッドで更新処理。
        codeの値が同じである（＝すでに登録されている）レコードがない場合には追加処理が行われる。
        */
        // 更新（追加）の実行
        service.updateCountry(code, name, population);

        // 一覧画面にリダイレクト
        return "redirect:/country/list";
    }


    // ----- 削除画面 -----
    @GetMapping(value = { "/delete", "/delete/{code}/" } ) // 修正
    public String deleteCountryForm(@PathVariable(name = "code", required = false) String code, Model model) {
        if (code != null) {
            // URLでcodeが指定されているとき
            service.getCountry(code);
            model.addAttribute("code", code);
        }
        // country/delete.htmlに画面遷移
        return "country/delete";

    }


    // ----- 削除 -----
    @PostMapping("/delete")
    public String deleteCountry(@RequestParam("code") String code, Model model) {
        // 削除
        service.deleteCountry(code);

        // 一覧画面にリダイレクト
        return "redirect:/country/list";
    }

    // ----- 追加:ここまで -----

}