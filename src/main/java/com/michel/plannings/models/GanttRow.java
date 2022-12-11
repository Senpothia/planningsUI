package com.michel.plannings.models;

public class GanttRow implements Comparable <GanttRow>{
	
	private String taskId;
	private String taskName;
	private String ressource;
	private String startDate;
	private String endDate;
	private int duration;
	private int percent;
	private String dependencies;

	public GanttRow() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public GanttRow(String taskId, String taskName, String ressource, String startDate, String endDate, int duration,
			int percent, String dependencies) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.ressource = ressource;
		this.startDate = startDate;
		this.endDate = endDate;
		this.duration = duration;
		this.percent = percent;
		this.dependencies = dependencies;
	}



	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public String getDependencies() {
		return dependencies;
	}

	public void setDependencies(String dependencies) {
		this.dependencies = dependencies;
	}

	public String getRessource() {
		return ressource;
	}

	public void setRessource(String ressource) {
		this.ressource = ressource;
	}



	@Override
	public String toString() {
		return "GanttRow [taskId=" + taskId + ", taskName=" + taskName + ", ressource=" + ressource + ", startDate="
				+ startDate + ", endDate=" + endDate + ", duration=" + duration + ", percent=" + percent
				+ ", dependencies=" + dependencies + "]";
	}



	@Override
	public int compareTo(GanttRow ganttRow) {
		int id = Integer.parseInt(this.taskId);
		int idRow = Integer.parseInt(ganttRow.taskId);
		return (id - idRow);
	}
	
	


}
