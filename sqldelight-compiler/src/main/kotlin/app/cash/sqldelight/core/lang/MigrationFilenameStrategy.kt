package app.cash.sqldelight.core.lang

enum class MigrationFilenameStrategy {
  /**
   * Create a migration file named "1.sqm" to define the migration ***from***
   * version 1 to a new auto-incremented version - in this case 2. This is the
   * default behaviour.
   */
  VersionFrom,

  /**
   * Create a migration file named "2.sqm" to define the migration from the
   * previous version ***to*** version 2. These can be arbitrary integers,
   * not necessarily an incrementing count. Use this in case you need to define
   * custom migration version numbers for any reason.
   */
  VersionTo;

  companion object {
    val Default = VersionFrom
  }
}
