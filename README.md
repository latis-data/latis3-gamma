# Summary

*LaTiS version 3* is a rewrite of the *Functional Data Model* and *Algebra* based on lessons learned from version 2, and with a bit more inspiration from Functional Programming. It will incubate as *LaTiS3 version 0.x* until we are ready to finalize the API and release it as *LaTiS version 3.x*.

The primary distinction of LaTiS 3 is the perspective of a *Dataset* as a sequence of *Samples* with *Operations* that implement the *Functional Algebra* in terms of pure functions applied to *Samples*. Special implementations of *FunctionData* facilitate *nested Functions* and optimal operations on data.

Another distinction of LaTiS 3 is to completely de-couple the model from the data.  The *Data* algebraic data type has no knowledge of the *DataType* algebraic data type and vice versa.  Only at the *Dataset* level do the two concepts become combined.

The usual collection of *Adapters* and *Writers* are conceptually similar but take better advantage of the new data model implementation. The *Adapter* life cycle, in particular, is much cleaner. This new version of LaTiS will continue to support the same service interface (and more).

# Glossary
Items that are capitalized are Scala class names.

* **arity** - The number of un-flattened *DataTypes* in the domain of a function.  
For example, a function with a domain of (x, y) and wavelength with a range of irradiance has an arity of 2.
* **Data** - A recursive algebraic data type consisting of *ScalarData*, *TupleData*, and *FunctionData*. Unlike the *DataType*, which describes the model, the *Data* objects contain actual values representing measurements.  Since *TupleData* and *FunctionData* contain multiple *Data* objects they can be represented as trees just like DataType objects.
* **Dataset** - A *Dataset* instance consists of three things:
  1. *MetaData* for the dataset itself: A list of key/value pairs.
  2. The model for the dataset,  which is itself a *DataType* object.
  3. The data itself, which itself is a *FunctionData* object, which itself is an iterator of *Samples*.
* **DataType** - A recursive algebraic data type consisting of *Scalars*, *Tuples*, and *Functions*.  The *DataType* is purely in the realm of the model.  Since *Tuples* and Functions themselves contain multiple *DataType* objects they can be nested with arbitrary complexity, meaning a *DataType* can be represented as a tree.  
Also, every *DataType* must contain a *MetaData* object.
* **dimensionality** - The number of flattened *DataTypes* in the domain of a function.  
For example, a function with a domain of (x, y) and wavelength with a range of irradiance has a dimensionality of 3.
* **domain** - The independent variables of a function.
* **fdml** - (Functional Datamodel Markup Language) An xml file format to describe the contents of a *Dataset*.  
See the FDML Schema section below.
* **Function** - A *Function* is a *DataType* consisting of exactly two *DataType* objects, a domain and a range. Function is a member of the DataType algebraic data type.
* **functional data model** - Datsets consist of ordered sequences of samples that are decoupled from metadata.  Operations that implement the functional algebra can be performed on these samples to create new datasets.
* **Metadata** - An instance of *Metadata* is simply a list of key/value pairs to describe a *DataType* or *Dataset*.  Every *Scalar* object is required to contain a *MetaData* object, but *MetaData* objects may be empty for *Tuple* and *Function* objects.
* **range** - The dependent variables of a function.
* **Sample** - A *Sample* is no more than the dimensionality of a dataset followed by an array of *Data* objects.  Nested tuples are not allowed in a *Sample* but *ScalarData* and *FunctionData* are.
* **Scalar** - A *Scalar* is a *DataType* consisting of a single atomic variable.  A *DataType* of arbitrary complexity must ultimately consist of *Scalar* objects as the leaf nodes of the representation tree.  *Scalar* is a member of the *DataType* algebraic data type.
* **Tuple** - A *Tuple* is a *DataType* consisting of a list of *DataType* objects.  Unlike a function, the *Datatype* objects in a *Tuple* are associated but are not dependent on each other.  
An example is a 2D point consisting of the x,y *Tuple*.  The scalars x and y are associated but x is not a function of y nor is y a function of x. Elements of a *Tuple* also do not have to be of the same type.  Tuple is a member of the *DataType* algebraic data type.

# Design Decisions
* *DataType* and *Data* objects are completely decoupled:  
  * Con: Trying to maintain two identical tree structures is difficult.
  * Pro: Computation efficiencies require data to be unencumbered with metadata and model concepts. 
* *TupleData* is implemented as a tuple consisting of the first *Data* item and all of the rest:  
  * Con: Seems arbitrary to require both a single *Data* item followed by a list of additional *Data* items.
  * Pro: Ensures that at least one Data item is in the *TupleData*.
* *Sample* objects are not implemented as a tuple of domain and range, as a *Function* is, but are instead implemented as an integer representing dimensionality followed by the domain and range combined into a single list:  
  * Con: The dimensionality number feels arbitrary. Simply breaking a *Sample* into domain and range would be more elegant.
  * Pro: Computations performed on *Samples* must be efficient.  Extra structure to the data will just slow down the application of functions to the data.
* Sample objects can contain nested *FunctionData* objects, but not nested *TupleData* objects:  
  * Con: Inconsistency between *TupleData* and *FunctionData*.
  * Pro: The model contains all the information needed to represent nested tuples.



