Feature: Käyttäjänä voin lisätä vinkkejä kirjastoon

  Scenario: Lisää vinkki ilman tageja ja URLia
    Given Ohjelma on käynnistetty
    When Käyttäjä valitsee vinkin lisäämisen ja syöttää otsikoksi "otsikko" ja kommentiksi "kommentti" ja jättää tagit ja URLin tyhjäksi
    Then Kirjastoon on lisätty vinkki, jolla on otsikkona "otsikko" ja kommenttina "kommentti"
    Then Ohjelma pysähtyy

  Scenario: Vinkin lisääminen ilman otsikkoa ja kommenttia ei onnistu
    Given Ohjelma on käynnistetty
    When Käyttäjä valitsee vinkin lisäämisen ja jättää otsikon tyhjäksi ja jättää kommentin tyhjäksi ja jättää tagit tyhjäksi ja jättää URLin tyhjäksi
    Then Ohjelma tulostaa "Vinkin lisääminen epäonnistui:"
    Then Ohjelma pysähtyy

