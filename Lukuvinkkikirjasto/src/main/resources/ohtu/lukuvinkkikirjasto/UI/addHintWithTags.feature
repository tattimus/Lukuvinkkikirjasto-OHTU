Feature: Lisätessäni vinkin voin halutessani määritellä sille tageja

  Scenario: Lisää vinkki ilman tageja
    Given Ohjelma on käynnistetty
    When Käyttäjä valitsee vinkin lisäämisen ja syöttää otsikoksi "otsikko" ja kommentiksi "kommentti" ja tageiksi ""
    Then Kirjastoon on lisätty vinkki, jolla on otsikkona "otsikko" ja kommenttina "kommentti" ja ei tageja
    Then Ohjelma pysähtyy

  Scenario: lisää vinkki yhdellä tagilla
    Given Ohjelma on käynnistetty
    When Käyttäjä valitsee vinkin lisäämisen ja syöttää otsikoksi "otsikko" ja kommentiksi "kommentti" ja tageiksi "tagi"
    Then Kirjastoon on lisätty vinkki, jolla on otsikkona "otsikko" ja kommenttina "kommentti" ja tagina "tagi"
    Then Ohjelma pysähtyy

  Scenario: Lisää vinkki useammalla tagilla
    Given Ohjelma on käynnistetty
    When Käyttäjä valitsee vinkin lisäämisen ja syöttää otsikoksi "otsikko" ja kommentiksi "kommentti" ja tageiksi "tagi1, tagi2"
    Then Kirjastoon on lisätty vinkki, jolla on otsikkona "otsikko" ja kommenttina "kommentti" ja tagina "tagi1,tagi2"
    Then Ohjelma pysähtyy


