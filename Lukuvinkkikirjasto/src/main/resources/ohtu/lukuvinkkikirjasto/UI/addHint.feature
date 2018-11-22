Feature: Käyttäjänä voin lisätä vinkkejä kirjastoon

  Scenario: Lisää vinkki
    Given Ohjelma on käynnistetty
    When Käyttäjä valitsee vinkin lisäämisen ja syöttää otsikoksi "otsikko" ja kommentiksi "kommentti" ja tageiksi "tag, toinen tag"  
    Then Kirjastoon on lisätty vinkki, jolla on otsikkona "otsikko" ja kommenttina "kommentti"
    Then Ohjelma pysähtyy
