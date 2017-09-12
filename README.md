# gradle-play-autoincrement-plugin
A Gradle plugin to allow manipulating SQLite databases in the buildScript

## Usage
On your `build.gradle` add:
```groovy
plugins {
    id 'com.github.gmazzo.sqlite'
}
```
Visit [Gradle plugins](https://plugins.gradle.org/plugin/com.github.gmazzo.sqlite) for further details on how to apply it

After applying, the standard `groovy.sql.Sql` can be used to open or create any SQLite database with:
```groovy
Sq.newInstance("jdbc:sqlite:" + databaseFile, "org.sqlite.JDBC")
```
Or you can use this shortcut function:
```groovy
openSQLiteDatabase(databaseFile)
```

### Example
```groovy
plugins {
    id 'com.github.gmazzo.sqlite'
}

task createSampleDatabase {
    def databaseFile = file("$buildDir/sample.db")

    outputs.file databaseFile

    doLast {
        def db = openSQLiteDatabase(databaseFile)
        db.execute 'CREATE TABLE sample (name TEXT NOT NULL, value INTEGER NOT NULL)'
        db.execute 'INSERT INTO sample VALUES (\'AAA\', 0)'
        db.execute 'INSERT INTO sample VALUES (\'BBB\', 1)'
    }

}
```

## Configuration
By default, this plugins relies in the `org.xerial:sqlite-jdbc:3.20.0` SQLite driver.
If you want to change it or update its version, add:
```groovy
sqlite {
    driverDependency 'org.xerial:sqlite-jdbc:<newVersion>'
}
```
