This is a collection of factory methods from the

#Box & Wisker
Create a box & wisker chart

	createBoxAndWhiskerChart(String title, String categoryAxisLabel, String valueAxisLabel, BoxAndWhiskerCategoryDataset dataset, boolean legend)
Creates and returns a default instance of a box and whisker chart based on data from a BoxAndWhiskerCategoryDataset.

	createBoxAndWhiskerChart(String title, String timeAxisLabel, String valueAxisLabel, BoxAndWhiskerXYDataset dataset, boolean legend)
Creates and returns a default instance of a box and whisker chart.

#OHLC Data

	createCandlestickChart(String title, String timeAxisLabel, String valueAxisLabel, OHLCDataset dataset, boolean legend)
Creates and returns a default instance of a candlesticks chart.

	createHighLowChart(String title, String timeAxisLabel, String valueAxisLabel, OHLCDataset dataset, boolean legend)
Creates and returns a default instance of a high-low-open-close chart.

	createHighLowChart(String title, String timeAxisLabel, String valueAxisLabel, OHLCDataset dataset, Timeline timeline, boolean legend)
Creates and returns a default instance of a high-low-open-close chart with a special timeline.

#Interval Dataset

	createHistogram(String title, String xAxisLabel, String yAxisLabel, IntervalXYDataset dataset, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls)
Creates a histogram chart.

	createXYBarChart(String title, String xAxisLabel, boolean dateAxis, String yAxisLabel, IntervalXYDataset dataset, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls)
Creates and returns a default instance of an XY bar chart.

#Multi Pie
	createMultiplePieChart(String title, CategoryDataset dataset, TableOrder order, boolean legend, boolean tooltips, boolean urls)
Creates a chart that displays multiple pie plots.

	createMultiplePieChart3D(String title, CategoryDataset dataset, TableOrder order, boolean legend, boolean tooltips, boolean urls)
Creates a chart that displays multiple pie plots.

#Polar
	createPolarChart(String title, XYDataset dataset, boolean legend, boolean tooltips, boolean urls)
Creates a polar plot for the specified dataset (x-values interpreted as angles in degrees).

#Stacked
	createStackedAreaChart(String title, String categoryAxisLabel, String valueAxisLabel, CategoryDataset dataset, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls)
Creates a stacked area chart with default settings.

	createStackedBarChart(String title, String domainAxisLabel, String rangeAxisLabel, CategoryDataset dataset, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls)
Creates a stacked bar chart with default settings.

	createStackedBarChart3D(String title, String categoryAxisLabel, String valueAxisLabel, CategoryDataset dataset, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls)
Creates a stacked bar chart with a 3D effect and default settings.

	createStackedXYAreaChart(String title, String xAxisLabel, String yAxisLabel, TableXYDataset dataset, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls)
Creates a stacked XY area plot.

#???
I don't know where to group these.  They're just weird.

	createWaferMapChart(String title, WaferMapDataset dataset, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls)
Creates a wafer map chart.

	createWindPlot(String title, String xAxisLabel, String yAxisLabel, WindDataset dataset, boolean legend, boolean tooltips, boolean urls)
Creates a wind plot with default settings.

	createBubbleChart(String title, String xAxisLabel, String yAxisLabel, XYZDataset dataset, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls)
Creates a bubble chart with default settings.

	createGanttChart(String title, String categoryAxisLabel, String dateAxisLabel, IntervalCategoryDataset dataset, boolean legend, boolean tooltips, boolean urls)
Creates a Gantt chart using the supplied attributes plus default values where required.

	createTimeSeriesChart(String title, String timeAxisLabel, String valueAxisLabel, XYDataset dataset, boolean legend, boolean tooltips, boolean urls)
Creates and returns a time series chart.
