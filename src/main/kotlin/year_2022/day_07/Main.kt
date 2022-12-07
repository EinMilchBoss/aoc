package year_2022.day_07

import utils.test
import java.io.File

data class Dialog(val input: String, val output: List<String>)

data class FileSystem(val root: Directory = Directory("/", null)) {
    fun filterDirectories(predicate: (size: Int) -> Boolean): List<Directory> {
        fun iterate(currentDirectory: Directory): List<Directory> {
            return if (predicate(currentDirectory.size())) {
                listOf(currentDirectory) + currentDirectory.directories.flatMap { directory -> iterate(directory) }
            } else
                currentDirectory.directories.flatMap { directory -> iterate(directory) }
        }
        return iterate(root)
    }

    data class File(val name: String, val size: Int) {
        companion object {
            fun fromEntry(entry: String): File =
                entry.split(" ").let { (size, name) ->
                    File(name, size.toInt())
                }
        }
    }

    data class Directory(
        val name: String,
        val parent: Directory?,
        val files: MutableList<File> = mutableListOf(),
        val directories: MutableList<Directory> = mutableListOf()
    ) {
        companion object {
            fun fromEntry(entry: String, parentDirectory: Directory): Directory =
                Directory(
                    entry.split(" ").last(),
                    parentDirectory
                )

            fun isEntryDirectory(entry: String): Boolean =
                entry.startsWith("dir")
        }

        fun addElements(content: List<String>): Directory =
            apply {
                content.forEach { entry ->
                    if (isEntryDirectory(entry)) directories.add(fromEntry(entry, this))
                    else files.add(File.fromEntry(entry))
                }
            }

        fun changeDirectory(name: String): Directory =
            if (name != "..") directories.first { it.name == name }
            else parent!!

        fun size(): Int =
            files.sumOf { it.size } + directories.sumOf { it.size() }
    }
}

fun FileSystem.explore(dialogs: List<Dialog>): FileSystem {
    tailrec fun iterate(currentDirectory: FileSystem.Directory, dialogIndex: Int) {
        if (dialogIndex > dialogs.lastIndex) return

        dialogs[dialogIndex].let { (input, output) ->
            if (input.startsWith("cd")) {
                return iterate(
                    currentDirectory.changeDirectory(input.split(' ').last()),
                    dialogIndex + 1
                )
            }
            if (input.startsWith("ls")) {
                currentDirectory.addElements(output)
                return iterate(currentDirectory, dialogIndex + 1)
            }
        }
    }
    return apply { iterate(root, 0) }
}

fun List<String>.parse(): List<Dialog> =
    joinToString("\n")
        .split("$")
        .filterNot(String::isBlank)
        .drop(1)
        .map { dialog ->
            dialog.trim()
                .split("\n")
                .run {
                    Dialog(first(), drop(1))
                }
        }

fun solveFirst(input: List<String>): String {
    val inter = FileSystem().explore(input.parse())
        .filterDirectories { it <= 100_000 }
        .sumOf { it.size() }

    return inter.toString()
}

fun solveSecond(input: List<String>): String {
    val fileSystem = FileSystem().explore(input.parse())
    val maxUsedSpace = 40_000_000
    val toBeDeleted = fileSystem.root.size() - maxUsedSpace

    val inter = fileSystem
        .filterDirectories { it >= toBeDeleted }
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