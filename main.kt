fun <T> dijkstra(graph: Graph<T>, start: T): Map<T, T?> {
    val S: MutableSet<T> = mutableSetOf() // subset dari simpul yang kita ketahui jarak sebenarnya, contoh [A, B, C, D, E]

    /*
     * variable delta mewakili jumlah dari banyaknya jalur terpendek
     * Isi pertama adalah infinity dengan Int.MAX_VALUE untuk nanti dibandingkan
     */
    val delta = graph.vertices.map { it to Double.MAX_VALUE }.toMap().toMutableMap()
    delta[start] = 0.0

    /* previous adalah variable untuk menampung path yang telah dikunjungi
     * inisialiasasi awal semua isi menjadi null
     */
    val previous: MutableMap<T, T?> = graph.vertices.map { it to null }.toMap().toMutableMap()

    // Melakukan perulangan jika S != subset simpul, contoh [A, B] != [A, B, C, D]
    while (S != graph.vertices) {
        // v adalah simpul terdekat yang belum dikunjungi
        val v: T = delta
            .filter { !S.contains(it.key) }
            .minByOrNull { it.value }!!
            .key

        graph.edges.getValue(v).minus(S).forEach { neighbor ->
            val newPath = delta.getValue(v) + graph.weights.getValue(Pair(v, neighbor))

            if (newPath < delta.getValue(neighbor)) {
                delta[neighbor] = newPath
                previous[neighbor] = v
            }
        }

        S.add(v)
    }

    return previous.toMap()
}

// fungsi untuk menampilkan shortest path dari fungsi dijkstra yang mengembalikan nilai previous path
fun <T> shortestPath(shortestPathTree: Map<T, T?>, start: T, end: T): List<T> {
    fun pathTo(start: T, end: T): List<T> {
        if (shortestPathTree[end] == null) return listOf(end)
        return listOf(pathTo(start, shortestPathTree[end]!!), listOf(end)).flatten()
    }

    return pathTo(start, end)
}

// Mengambil isian yang unik menjadikannya set, contoh [[A, B], [A, C]] menjadi [A, B, C]
fun <T> List<Pair<T, T>>.getUniqueValuesFromPairs(): Set<T> = this
    .map { (a, b) -> listOf(a, b) }
    .flatten()
    .toSet()

// Mengambil isian yang unik untuk dikelompokan dengan data awalnya, contoh [[A, B], [A, C], [A, D]] menjadi [A=[B, C, D]]
fun <T> List<Pair<T, T>>.getUniqueValuesFromPairs(predicate: (T) -> Boolean): Set<T> = this
    .map { (a, b) -> listOf(a, b) }
    .flatten()
    .filter(predicate)
    .toSet()

data class Graph<T>(
    val vertices: Set<T>,
    val edges: Map<T, Set<T>>,
    val weights: Map<Pair<T, T>, Double>
) {
    constructor(weights: Map<Pair<T, T>, Double>): this(
        vertices = weights.keys.toList().getUniqueValuesFromPairs(),
        edges = weights.keys
            .groupBy { it.first }
            .mapValues { it.value.getUniqueValuesFromPairs { x -> x != it.key } }
            .withDefault { emptySet() },
        weights = weights
    )
}


fun main() {
    val weights = mapOf(
        Pair("V1", "V2") to 32.8565,  
        Pair("V1", "V4") to 31.5029, 
        Pair("V2", "V3") to 32.3290, 
        Pair("V3", "V5") to 34.3911, 
        Pair("V4", "V5") to 31.6055, 
        Pair("V5", "V7") to 32.8565, 
        Pair("V6", "V7") to 19.0318, 
        Pair("V7", "V8") to 43.9895, 
        Pair("V8", "V9") to 22.7863, 
        Pair("V8", "V12") to 33.5378, 
        Pair("V9", "V13") to 45.5436, 
        Pair("V10", "V11") to 30.3430, 
        Pair("V10", "V16") to 35.5648, 
        Pair("V11", "V12") to 24.8340, 
        Pair("V11", "V14") to 35.6063, 
        Pair("V12", "V13") to 19.6697, 
        Pair("V12", "V15") to 23.5461, 
        Pair("V13", "V27") to 42.3140, 
        Pair("V14", "V15") to 22.8862, 
        Pair("V14", "V17") to 32.8370, 
        Pair("V15", "V19") to 25.8197, 
        Pair("V16", "V17") to 23.0765, 
        Pair("V16", "V20") to 36.4917, 
        Pair("V17", "V18") to 81.6990, 
        Pair("V18", "V19") to 24.6396, 
        Pair("V18", "V22") to 28.0496, 
        Pair("V19", "V22") to 23.6371, 
        Pair("V20", "V21") to 34.4571, 
        Pair("V20", "V132") to 42.2569, 
        Pair("V20", "V52") to 27.5271, 
        Pair("V21", "V22") to 93.6077, 
        Pair("V21", "V23") to 47.5599, 
        Pair("V23", "V24") to 31.1989, 
        Pair("V23", "V25") to 38.4149, 
        Pair("V24", "V26") to 30.0146, 
        Pair("V25", "V26") to 28.6462, 
        Pair("V25", "V35") to 39.0438, 
        Pair("V27", "V28") to 56.0263, 
        Pair("V28", "V29") to 49.3978, 
        Pair("V29", "V30") to 57.6212, 
        Pair("V30", "V31") to 39.2643, 
        Pair("V30", "V34") to 27.9297, 
        Pair("V31", "V33") to 21.1346, 
        Pair("V32", "V33") to 24.3812, 
        Pair("V32", "V38") to 25.7031, 
        Pair("V33", "V34") to 36.0158, 
        Pair("V34", "V35") to 35.8973, 
        Pair("V35", "V36") to 31.2766, 
        Pair("V36", "V37") to 26.9776, 
        Pair("V36", "V49") to 22.3514, 
        Pair("V37", "V42") to 23.5547, 
        Pair("V38", "V39") to 48.3365, 
        Pair("V39", "V40") to 32.6583, 
        Pair("V39", "V80") to 31.7648, 
        Pair("V40", "V41") to 41.5707, 
        Pair("V40", "V43") to 29.8154, 
        Pair("V41", "V42") to 33.7468, 
        Pair("V42", "V43") to 34.5449, 
        Pair("V43", "V45") to 31.8310, 
        Pair("V44", "V133") to 42.4931, 
        Pair("V45", "V46") to 40.9725, 
        Pair("V46", "V47") to 43.2952, 
        Pair("V46", "V57") to 40.0823, 
        Pair("V47", "V48") to 62.2909, 
        Pair("V48", "V49") to 40.0890, 
        Pair("V49", "V50") to 78.3707, 
        Pair("V50", "V51") to 31.2234, 
        Pair("V51", "V52") to 29.4816, 
        Pair("V51", "V56") to 58.3793, 
        Pair("V52", "V53") to 37.0839, 
        Pair("V53", "V54") to 43.1303, 
        Pair("V53", "V55") to 23.5533, 
        Pair("V54", "V62") to 25.0105, 
        Pair("V55", "V56") to 38.7697, 
        Pair("V55", "V60") to 35.5762, 
        Pair("V56", "V58") to 36.1349, 
        Pair("V57", "V58") to 24.9189, 
        Pair("V58", "V59") to 28.6239, 
        Pair("V59", "V60") to 39.8260, 
        Pair("V59", "V68") to 26.3791, 
        Pair("V60", "V61") to 37.0247, 
        Pair("V61", "V62") to 52.2948, 
        Pair("V62", "V63") to 22.5428, 
        Pair("V63", "V64") to 25.2771, 
        Pair("V64", "V65") to 26.7408, 
        Pair("V64", "V114") to 26.0722, 
        Pair("V65", "V66") to 45.8048, 
        Pair("V65", "V70") to 23.3248, 
        Pair("V66", "V67") to 24.8963, 
        Pair("V67", "V68") to 46.6303, 
        Pair("V68", "V70") to 25.8550, 
        Pair("V69", "V77") to 22.4023, 
        Pair("V69", "V133") to 26.1170, 
        Pair("V70", "V71") to 38.3998, 
        Pair("V70", "V77") to 34.7244, 
        Pair("V71", "V75") to 27.7550, 
        Pair("V71", "V72") to 26.5172, 
        Pair("V72", "V74") to 27.8822, 
        Pair("V73", "V75") to 55.5571, 
        Pair("V74", "V79") to 25.2044, 
        Pair("V74", "V92") to 31.9471, 
        Pair("V75", "V77") to 28.2666, 
        Pair("V76", "V84") to 24.6016, 
        Pair("V76", "V77") to 31.0289, 
        Pair("V78", "V83") to 31.4415, 
        Pair("V79", "V85") to 24.7006, 
        Pair("V79", "V86") to 24.3535, 
        Pair("V80", "V81") to 28.9610, 
        Pair("V81", "V82") to 26.6610, 
        Pair("V81", "V88") to 31.2684, 
        Pair("V82", "V84") to 33.0794, 
        Pair("V82", "V87") to 27.1160, 
        Pair("V83", "V84") to 31.9306, 
        Pair("V84", "V85") to 40.4575, 
        Pair("V85", "V86") to 56.6904, 
        Pair("V86", "V134") to 49.4946, 
        Pair("V87", "V88") to 23.4430, 
        Pair("V88", "V89") to 31.0812, 
        Pair("V89", "V90") to 36.4237, 
        Pair("V88", "V97") to 28.8027, 
        Pair("V90", "V91") to 27.1996, 
        Pair("V90", "V95") to 27.7371, 
        Pair("V91", "V134") to 34.4435, 
        Pair("V91", "V94") to 31.9757, 
        Pair("V92", "V134") to 47.9834, 
        Pair("V92", "V93") to 40.7303, 
        Pair("V93", "V94") to 29.8947, 
        Pair("V93", "V104") to 28.7037, 
        Pair("V94", "V95") to 23.1517, 
        Pair("V95", "V96") to 29.6778, 
        Pair("V96", "V100") to 22.2303, 
        Pair("V96", "V101") to 19.1448, 
        Pair("V97", "V98") to 44.8355, 
        Pair("V98", "V99") to 33.5629, 
        Pair("V101", "V102") to 32.8086, 
        Pair("V101", "V131") to 26.0692, 
        Pair("V102", "V103") to 35.4191, 
        Pair("V102", "V127") to 22.4880, 
        Pair("V103", "V105") to 38.8141, 
        Pair("V104", "V105") to 41.8521, 
        Pair("V105", "V106") to 78.1145, 
        Pair("V106", "V107") to 40.7907, 
        Pair("V106", "V122") to 29.9766, 
        Pair("V107", "V108") to 49.5708, 
        Pair("V107", "V111") to 29.7873, 
        Pair("V108", "V109") to 39.4591, 
        Pair("V108", "V110") to 34.6290, 
        Pair("V109", "V110") to 41.1782, 
        Pair("V109", "V112") to 28.0367, 
        Pair("V110", "V119") to 34.0287, 
        Pair("V111", "V120") to 35.1185, 
        Pair("V112", "V113") to 40.7942, 
        Pair("V112", "V118") to 21.8003, 
        Pair("V113", "V114") to 27.5104, 
        Pair("V114", "V115") to 22.8668, 
        Pair("V115", "V116") to 26.1176, 
        Pair("V116", "V117") to 24.3391, 
        Pair("V117", "V118") to 24.1528, 
        Pair("V118", "V119") to 24.7563, 
        Pair("V119", "V120") to 33.1274, 
        Pair("V120", "V125") to 23.5832, 
        Pair("V121", "V123") to 23.0647, 
        Pair("V122", "V123") to 27.9552, 
        Pair("V123", "V124") to 37.4585, 
        Pair("V124", "V125") to 30.6110, 
        Pair("V124", "V126") to 29.6076, 
        Pair("V126", "V127") to 22.8485, 
        Pair("V128", "V129") to 32.5073, 
        Pair("V128", "V130") to 23.2006, 
        Pair("V129", "V130") to 24.8408, 
        Pair("V130", "V131") to 24.9933
    )

    val reverseWeights = weights.map { (key, value) ->
        Pair(key.second, key.first) to value
    }.toMap()

    val mergeTheWeights = weights + reverseWeights

    val start = "V1"
    val end = "V129"
    val shortestPathTree = dijkstra(Graph(mergeTheWeights), start)

    println(shortestPath(shortestPathTree, start, end))
}