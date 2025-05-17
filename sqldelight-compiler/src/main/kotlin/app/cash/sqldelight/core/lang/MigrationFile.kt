package app.cash.sqldelight.core.lang

import app.cash.sqldelight.core.SqlDelightFileIndex
import com.alecstrong.sql.psi.core.SqlFileBase
import com.intellij.psi.FileViewProvider

class MigrationFile(
  viewProvider: FileViewProvider,
  private val filenameStrategy: MigrationFilenameStrategy,
) : SqlDelightFile(viewProvider, MigrationLanguage) {
  val version: Long by lazy {
    val versionTo = name
      .substringBeforeLast(".$MIGRATION_EXTENSION")
      .filter { it in '0'..'9' }
      .toLongOrNull()
      ?: 0
    when (filenameStrategy) {
      MigrationFilenameStrategy.VersionFrom -> versionTo + 1
      MigrationFilenameStrategy.VersionTo -> versionTo
    }
  }

  internal fun sqlStatements() = sqlStmtList!!.stmtList

  override val order
    get() = version

  override fun getFileType() = MigrationFileType

  override fun baseContributorFiles(): List<SqlFileBase> {
    val module = module
    if (module == null || SqlDelightFileIndex.getInstance(module).deriveSchemaFromMigrations) {
      return emptyList()
    }

    return listOfNotNull(findDbFile())
  }
}
