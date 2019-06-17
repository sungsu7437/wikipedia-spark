import org.apache.spark.rdd.RDD

case class WikipediaArticle(title: String, text: String) {
    def mentionsLanguage(lang: String): Boolean = text.split(' ').contains(lang)
}

def parse(line: String): WikipediaArticle = {
    val subs = "</title><text>"
    val i = line.indexOf(subs)
    val title = line.substring(14, i)
    val text  = line.substring(i + subs.length, line.length-16)
    WikipediaArticle(title, text)
}

val langs = List(
"JavaScript", "Java", "PHP", "Python", "C#", "C++", "Ruby", "CSS",
"Objective-C", "Perl", "Scala", "Haskell", "MATLAB", "Clojure", "Groovy")



def occurrencesOfLang(lang: String, rdd: RDD[WikipediaArticle]): Int = ??

def rankLangs(langs: List[String], rdd: RDD[WikipediaArticle]): List[(String, Int)] = ??

def makeIndex(langs: List[String], rdd: RDD[WikipediaArticle]): RDD[(String, Iterable[WikipediaArticle])] = ??

def rankLangsUsingIndex(index: RDD[(String, Iterable[WikipediaArticle])]): List[(String, Int)] = ??

def rankLangsReduceByKey(langs: List[String], rdd: RDD[WikipediaArticle]): List[(String, Int)] = ??


val wikiRdd = sc.textFile("hdfs:///dataset/wikipedia.dat").map(parse)

val langsRanked: List[(String, Int)] = rankLangs(langs, wikiRdd)

langsRanked.map(println)


def index: RDD[(String, Iterable[WikipediaArticle])] = makeIndex(langs, wikiRdd)

val langsRanked2: List[(String, Int)] = rankLangsUsingIndex(index)

langsRanked2.map(println)


val langsRanked3: List[(String, Int)] = rankLangsReduceByKey(langs, wikiRdd)

langsRanked3.map(println)


