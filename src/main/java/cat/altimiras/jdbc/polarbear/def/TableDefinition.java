package cat.altimiras.jdbc.polarbear.def;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableDefinition {
	private String name;

	//table content format (csv, json...)
	private String format;

	@JsonProperty("columns")
	private List<ColumnDefinition> columnDefinitions;

	@JsonProperty("partition")
	private PartitionDefinition partitionDefinition;

	private boolean ignoreWrongRowData = true;

	private int notFoundMaxLimit;

	private String separator = ","; //only csv format

	public TableDefinition() {
	}

	public TableDefinition(String name) {
		this.name = name;
	}

	public TableDefinition(String name, String format,
		List<ColumnDefinition> columnDefinitions, PartitionDefinition partitionDefinition, boolean ignoreWrongRowData,
		int notFoundMaxLimit) {
		this.name = name;
		this.format = format;
		this.columnDefinitions = columnDefinitions;
		this.partitionDefinition = partitionDefinition;
		this.ignoreWrongRowData = ignoreWrongRowData;
		this.notFoundMaxLimit = notFoundMaxLimit;
	}

	@JsonIgnore
	private Map<String, ColumnDefinition> columnByName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setColumnDefinitions(List<ColumnDefinition> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
	}

	public boolean isIgnoreWrongRowData() {
		return ignoreWrongRowData;
	}

	public void setIgnoreWrongRowData(boolean ignoreWrongRowData) {
		this.ignoreWrongRowData = ignoreWrongRowData;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public boolean isStarTable() {
		return this.partitionDefinition == null;
	}

	public boolean isMainTable() {
		return this.partitionDefinition != null;
	}

	public PartitionDefinition getPartition() {
		return partitionDefinition;
	}

	public void setPartition(PartitionDefinition partitionDefinition) {
		this.partitionDefinition = partitionDefinition;
	}

	public int getNotFoundMaxLimit() {
		return notFoundMaxLimit;
	}

	public void setNotFoundMaxLimit(int notFoundMaxLimit) {
		this.notFoundMaxLimit = notFoundMaxLimit;
	}

	public List<ColumnDefinition> getColumnDefinitions() {
		return columnDefinitions;
	}

	public Map<String, ColumnDefinition> getColumnsByName() {
		if (columnByName == null) {
			columnByName = new LinkedHashMap<>(columnDefinitions.size());
			for (int i = 0; i < columnDefinitions.size(); i++) {
				ColumnDefinition columnDefinition = columnDefinitions.get(i);
				columnDefinition.setPosition(i);
				columnByName.put(columnDefinition.getName(), columnDefinition);
			}
		}
		return columnByName;
	}

	public List<Integer> getPKPositions() {
		List<Integer> positions = new ArrayList<>();
		for (int i = 0; i < columnDefinitions.size(); i++) {
			if (columnDefinitions.get(i).isPK()) {
				positions.add(i);
			}
		}
		return positions;
	}

	public int numOfColumns() {
		return columnDefinitions.size();
	}
}