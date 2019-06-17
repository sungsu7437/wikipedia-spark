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


/** Problem 0:
*  Returns the number of articles on which the language `lang` occurs.
*  Hint: consider using method `mentionsLanguage` on `WikipediaArticle`
*/
def occurrencesOfLang(lang: String, rdd: RDD[WikipediaArticle]): Int = ??


/* Problem 1: Use `occurrencesOfLang` to compute the ranking of the languages
 *     (`val langs`) by determining the number of Wikipedia articles that
 *     mention each language at least once. Don't forget to sort the
 *     languages by their occurrence, in decreasing order!
 */
def rankLangs(langs: List[String], rdd: RDD[WikipediaArticle]): List[(String, Int)] = ??


/* Problem 2:
 * Compute an inverted index of the set of articles, mapping each language
 * to the Wikipedia pages in which it occurs.
 */
def makeIndex(langs: List[String], rdd: RDD[WikipediaArticle]): RDD[(String, Iterable[WikipediaArticle])] = ??


/* Problem 3: Compute the language ranking again, but now using the inverted index. Can you notice
 *     a performance improvement?
 */
def rankLangsUsingIndex(index: RDD[(String, Iterable[WikipediaArticle])]): List[(String, Int)] = ??


/* Problem 4: Use `reduceByKey` so that the computation of the index and the ranking are combined.
 *     Can you notice an improvement in performance compared to measuring *both* the computation of the index
 *     and the computation of the ranking?
 */
def rankLangsReduceByKey(langs: List[String], rdd: RDD[WikipediaArticle]): List[(String, Int)] = ??


val wikiRdd = sc.textFile("hdfs:///dataset/wikipedia.dat").map(parse)

val langsRanked: List[(String, Int)] = rankLangs(langs, wikiRdd)

langsRanked.map(println)


def index: RDD[(String, Iterable[WikipediaArticle])] = makeIndex(langs, wikiRdd)

val langsRanked2: List[(String, Int)] = rankLangsUsingIndex(index)

langsRanked2.map(println)


val langsRanked3: List[(String, Int)] = rankLangsReduceByKey(langs, wikiRdd)

langsRanked3.map(println)


