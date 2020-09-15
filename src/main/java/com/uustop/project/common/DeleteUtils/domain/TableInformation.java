package com.uustop.project.common.DeleteUtils.domain;


import com.uustop.framework.web.domain.BaseEntity;

/**
 * 数据库表
 *
 * @author xh
 */
public class TableInformation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 表名称
     */
    private String tableName;


    /**
     * 数据库名称
     */
    private String tableSchema;


    /**
     * 列名
     */
    private String columnName;

    /**
     * 列值
     */
    private Integer[] columnValue;

    /**
     * 可以忽略的数据表
     */
    private String ignoreTable;

    public TableInformation() {

    }

    public TableInformation(String tableName, String columnName, Integer[] columnValue) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    public TableInformation(String tableName, String tableSchema, String columnName, Integer[] columnValue) {
        this.tableName = tableName;
        this.tableSchema = tableSchema;
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    public TableInformation(String tableName, String columnName, Integer[] columnValue, String ignoreTable) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnValue = columnValue;
        this.ignoreTable = ignoreTable;
    }

    public String getIgnoreTable() {
        return ignoreTable;
    }

    public void setIgnoreTable(String ignoreTable) {
        this.ignoreTable = ignoreTable;
    }

    public Integer[] getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(Integer[] columnValue) {
        this.columnValue = columnValue;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
