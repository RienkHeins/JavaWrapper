# JavaWrapper
Contains a JavaWrapper that takes a file with unknown instances and classifies them using a randomforest weka model

Program can be used as followed:

java -jar JavaWrapper -f [filename or path]

or with the -h or --HELP commands to get a help menu

Repository comes with a default file that can be called with the command -f testdata/UnknownData.arff, own datafiles can be used but most be .arff or .csv files with the following attributes and class:

@attribute Uniformity_of_Cell_Size numeric
@attribute Uniformity_of_Cell_Shape numeric
@attribute Marginal_Adhesion numeric
@attribute Single_Epithelial_Cell_Size numeric
@attribute Bare_Nuclei numeric
@attribute Normal_Nucleoli numeric
@attribute Class {Benign,Malignant}
