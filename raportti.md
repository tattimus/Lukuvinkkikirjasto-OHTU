## Loppuraportti

*Samu Tatti, Jaakko Malkki, Karoliina Mietola, Adiel Lindroos, Stanislav 
Palchyk*

### Ongelmat

Ensimmäisten kahden sprintin aikana burndown-käyrä oli huonosti 
toteutettu eikä antanut mitään lisäarvoa projektille.

Kahden ensimmäisen sprintin backlogit olivat sekaisin ja niiden tehtävät 
eivät olleet tarpeeksi selkeitä, esimerkiksi vaatimusmäärittelyt olivat 
joissain 
tapauksissa hieman puutteelliset. 

Toisen sprintin jälkeen saimme korjattua burndown-käyrän selkeämpään ja 
automaattisesti päivittyvään muotoon sekä panostimme sprint backlogeihin 
niin, että jokaisella sprintillä on oma backlog ja että ne sisältävät 
tarpeeksi tietoa.

Teknisiä ongelmia projektissamme tuli testien ja jarin buildauksen 
kanssa. Jossain vaiheessa toisen sprintin jälkeen testien konfiguraatio 
meni jotenkin rikki, eikä testien suorittaminen onnistunut 
NetBeansissa. Testit kuitenkin toimivat komentorivillä ja Traviksessa, 
joten emme käyttäneet aikaa sen korjaamiseen. Ongelma oli mahdollisesti 
NetBeansin, Gradlen tai NetBeansin Gradle-lisäosan yhteensopimattomissa 
versioissa.
Jarin buildaus ei kolmannessa sprintissä onnistunut kaikkien 
tietokoneilla. Tämä johtui vanhasta Shadow pluginin versiosta, joka 
saatiin päivitettyä neljännessä sprintissä. Tutkimme mahdollisuutta 
buildata jarit suoraan Travis-palvelimella, mutta emme saaneet tätä 
toimimaan ajoissa.

Projektityöskentelyssä ongelmia tuli aikarajojen ja työnjaon kanssa. 
Työaika-arviot jäivät melko epätarkaksi ja joissain tapauksissa kuudessa 
työtunnissa oli vaikea pysyä. Toisaalta työnjako olisi voinut olla 
tehokkaampaa ja tämä olisi auttanut aikarajoissa pysymiseen. Tehtävien 
jakaminen järkevän pieniin kokonaisuuksiin tuntui kuitenkin vaikealta.

### Missä onnistuimme?

Saimme toteutettua kaikki halutut ominaisuudet aikarajoissa 
suunnitelmien mukaan.

Koodi pysyi siistinä ja helposti ylläpidettävänä, varsinkin toisessa 
sprintissä tehdyn refaktoroinnin jälkeen. Koodin tuotantoversio 
(`master`-haara GitHubissa) pysyi koko ajan toimivana.

Käytimme työskentelyssä hyödyksi GitHubin pull requesteja. Tämä tuntui 
auttavan koodin siisteyteen ja laatuun, kun koodin tarkasti ennen 
mergeämistä joku muu ryhmästä. Pull requestit auttoivat myös ymmärtämään 
sitä, mitä muut ovat tekemässä sekä pahoilta merge konflikteilta 
vältyttiin.

Ryhmätyöskentely onnistui hyvin ja kaikki tekivät sen mitä pitikin 
emmekä joutuneet käyttämään ylimääräistä aikaa työskentelyn koordinointiin.

Mielestämme projekti ylipäätään onnistui hyvin ja kaikki kohdatut 
ongelmat saatiin selvitettyä niin, etteivät ne estäneet projektin edistymistä.

### Parannettavaa

Testikoodista olisi voinut tehdä siistimpää. Varsinkin Cucumber-testejä 
ajava koodi jäi erittäin sotkuiseksi ja sisälsi paljon copypastea ja 
erikoisia viritelmiä.

Sprintin backlogin tehtävät olisi voinut jakaa paremmin niin, että 
jokaiselle rittäisi järkeviä tehtäviä. Yritimme jakaa tehtäviä niin, 
että niiden välillä olisi mahdollisimman vähän riippuvuuksia (esim. 
niin, että yksi henkilö toteuttaisi jonkin luokan ja tätä luokkaa 
vastaavan tietokantataulun), mutta tämä johti helposti siihen, että 
muutamalle henkilölle kasaantui tehtäviä epätasainen määrä. Osittain 
tähän vaikutti aikarajat sekä järkevän tekemisen vähäisyys.

Jätimme osan asioista viime tinkaan (esim. viimeisten pull requestien 
mergeäminen ja releasen tekeminen). Tästä ei projektissamme tullut 
ongelmaa, mutta olisi hyvin ollut mahdollista, että jokin asia ei olisi 
toiminutkaan ja sen korjaamiseen ei olisi ollut aika.

### Mitä opimme?

Opimme Scrumin käyttöä käytännön tilanteissa ja ymmärsimme miksi se on 
niin laajasti käytössä. Etenkin backlog havaittiin hyödylliseksi, koska 
sen avulla helppo seurata ja hallinnoida työskentelyä. 

Opimme myös versionhallinnan käyttöä projektissa, jossa työskentelee 
monta henkilöä.

### Mitä olisimme halunneet oppia?

Testien toteuttamiseen olisimme halunneet lisämateriaalia. Ymmärsimme 
Cucumberin ajatuksen, mutta käytännössä testien kirjoittaminen 
osoittautui hankalaksi, varsinkin kun projektin koko kasvoi. Myös 
tiettyjen vaikeasti testattavien tapausten (esim. luokat, jotka lataavat 
internetistä tietoja) käsittelyyn olisimme halunneet esimerkkejä.

### Mikä tuntui turhalta?

Burndown-käyrä tuntui turhalta näin pienessä ja lyhyessä projektissa, 
varsinkin kun tiimin kommunikoinnin ja backlogien perusteella pystyi 
helposti seuraamaan edistystä. Ymmärsimme kuitenkin miksi burndown-käyrä 
olisi hyödyllinen suuremmassa projektissa. 
