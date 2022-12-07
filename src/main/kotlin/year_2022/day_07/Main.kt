package year_2022.day_07

import utils.test
import java.io.File

//abstract class Command(vararg val params: String) {
//    abstract fun execute(): FileSystem
//}
//
//class ListCommand(vararg params: String) : Command(*params) {
//    override fun execute(fileSystem: FileSystem): FileSystem {
//
//    }
//}

fun String.isDirectory(): Boolean =
    startsWith("dir")

fun String.parseDirectory(parentDirectory: FileSystem.Directory): FileSystem.Directory =
    FileSystem.Directory(split(" ").last(), parentDirectory)

fun String.parseFile(): FileSystem.File =
    split(" ").let { (size, name) ->
        FileSystem.File(name, size.toInt())
    }

fun FileSystem.execute(dialogs: List<Dialog>): FileSystem {
    tailrec fun iterate(currentDirectory: FileSystem.Directory, dialogIndex: Int) {
        if (dialogIndex > dialogs.lastIndex) return

        dialogs[dialogIndex].let { (command, output) ->
            if (command.startsWith("ls")) {
                currentDirectory.directories.addAll(
                    output.filter { it.isDirectory() }
                        .map { it.parseDirectory(currentDirectory) }
                )
                currentDirectory.files.addAll(
                    output.filter { !it.isDirectory() }
                        .map(String::parseFile)
                )
                return iterate(currentDirectory, dialogIndex + 1)
            }
            if (command.startsWith("cd")) {
                return iterate(currentDirectory.changeDirectory(command.split(' ').last()), dialogIndex + 1)
            }
        }
    }
    return also { iterate(root, 0) }
}

data class Dialog(val command: String, val output: List<String>)

data class FileSystem(val root: Directory = Directory("/", null)) {
    // /
    // a
    //  3
    // b
    //  4
    // 1
    // 2
    // get current matches (1, 2)
    // go to each subdirectory and get matches ((3), (4))
    // flatten (3, 4)
    // add to local matches (1, 2, 3, 4)

    fun findDirectories(predicate: (size: Int) -> Boolean): List<Directory> {
        fun iterate(currentDirectory: Directory): List<Directory> {
            return if (predicate(currentDirectory.size())) {
                listOf(currentDirectory) + currentDirectory.directories.flatMap { directory -> iterate(directory) }
            } else
                currentDirectory.directories.flatMap { directory -> iterate(directory) }
        }
        return iterate(root)
    }

    data class File(val name: String, val size: Int)
    data class Directory(
        val name: String,
        val parentDirectory: Directory?,
        val files: MutableList<File> = mutableListOf(),
        val directories: MutableList<Directory> = mutableListOf()
    ) {
        fun changeDirectory(name: String): Directory =
            if (name != "..") directories.first { it.name == name }
            else parentDirectory!!

        fun size(): Int =
            files.sumOf { it.size } + directories.sumOf { it.size() }
    }
}

fun List<String>.parse(): List<Dialog> =
    joinToString("\n")
        .split("$")
        .filterNot { it.isBlank() }
        .drop(1)
        .map(String::trim)
        .map { dialog ->
            dialog.split("\n")
                .let { dialogLines ->
                    Dialog(dialogLines.first(), dialogLines.drop(1))
                }
        }
        .also(::println)

fun solveFirst(input: List<String>): String {
    // FileSystem
    // root folder as start
    // go through every line
    // if a command, process command with respective output
    val inter = FileSystem().execute(input.parse()).findDirectories { it <= 100_000}.also { println(it.size) }.sumOf { it.size() }

            // ls
            // cd
            // current folder = root
            // cd "/" = switch to root
            // ls


//    FileSystem().also { fileSystem ->
//        input.drop(1)
//            .forEach { line ->
//                if (line.isCommand())
//            }
//    }

    return inter.toString()
}

fun solveSecond(input: List<String>): String {
    val fileSystem = FileSystem().execute(input.parse())

    val updateSpace = 30_000_000
    val maxSpace = 70_000_000
    val maxUsedSpace = maxSpace - updateSpace
    val currentSpace = fileSystem.root.size()
    val toBeDeleted = currentSpace - maxUsedSpace
    val inter = FileSystem().execute(input.parse()).findDirectories { it >= toBeDeleted }.minOf { it.size() }

    return inter.toString()
}

fun main() {
    val pathPrefix = "./src/main/kotlin/year_2022/day_07"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "95437", ::solveFirst)}")
    println("Second test: ${test(exampleInput, "24933642", ::solveSecond)}")

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: ${solveSecond(input)}")
}