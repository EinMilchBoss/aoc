package year_2022.day_07

import utils.test
import java.io.File

data class Dialog(val input: String, val output: List<String>)

data class FileSystem(val root: Directory = Directory("/", null)) {
    fun filterDirectories(predicate: (Directory) -> Boolean): List<Directory> {
        fun iterate(currentDirectory: Directory): List<Directory> =
            if (predicate(currentDirectory)) listOf(currentDirectory) + currentDirectory.directories.flatMap(::iterate)
            else currentDirectory.directories.flatMap(::iterate)
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
            files.sumOf(File::size) + directories.sumOf(Directory::size)
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
        .split("\n$ ")
        .drop(1)
        .map { dialog ->
            dialog.split("\n")
                .run { Dialog(first(), drop(1)) }
        }

fun List<String>.solve(algorithm: FileSystem.() -> Int): String =
    FileSystem().explore(parse())
        .run(algorithm)
        .toString()

fun solveFirst(input: List<String>): String =
    input.solve {
        filterDirectories { it.size() <= 100_000 }
            .sumOf(FileSystem.Directory::size)
    }

fun solveSecond(input: List<String>): String =
    input.solve {
        filterDirectories { it.size() >= root.size() - 40_000_000 }
            .minOf(FileSystem.Directory::size)
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