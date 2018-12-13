
Feature: Voin lisätä ja hyödyntää tekijätietoja vinkeissä

    Scenario: Voin lisätä vinkille tekijän
        Given Ohjelma on käynnistetty
        When Käyttäjä valitsee vinkin lisäämisen ja syöttää otsikoksi "otsikko" ja kommentiksi "kommentti" ja vain tekijän "tekijä"
        Then Kirjastoon on lisätty vinkki, jolla on otsikkona "otsikko" ja kommenttina "kommentti" ja tekijänä "tekijä"
        Then Ohjelma pysähtyy

    Scenario: Voin nähdä vinkin tekijätiedon sen listauksessa:
        Given Ohjelma on käynnistetty
        And Tietokantaan on tallennettu vinkki id:llä 0, otsikolla "Teos", kuvauksella "Pakko lukea", tekijalla "tekija"
        When Käyttäjä valitsee vinkin  näyttämisen ja antaa id 0 ja antaa syötteen "n"
        Then Ohjelma tulostaa "Otsikko: Teos"
        And Ohjelma tulostaa "Kommentti: Pakko lukea"
        And Ohjelma tulostaa "Tekijät: tekija"
        Then Ohjelma pysähtyy