package year_2022.day_07

import utils.test
import java.io.File

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

fun solveFirst(input: List<String>): String {
    val inter = FileSystem().execute(input.parse())
        .findDirectories { it <= 100_000 }
        .sumOf { it.size() }

    return inter.toString()
}

fun solveSecond(input: List<String>): String {
    val fileSystem = FileSystem().execute(input.parse())
    val maxUsedSpace = 40_000_000
    val toBeDeleted = fileSystem.root.size() - maxUsedSpace

    val inter = fileSystem
        .findDirectories { it >= toBeDeleted }
        .minOf { it.size() }

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