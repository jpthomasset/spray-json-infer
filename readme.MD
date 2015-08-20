spray-json-infer
================

This program creates case classes and JsonProtocol based on an existing json document. The generated code can then be
used in a Scala application with the [spray-json](https://github.com/spray/spray-json) library

Usage
-----
From the sbt command line: run <arguments>
* -c <classname>    Where classname is the name of the main case class (Required)
* -f <filename>     Where filename is the name of a json file
* -u <url>          Where url is the name of a json file
* -s <string>       Where string is a valid json string
* -o <objectname>   Encapsulate code in an object with the given name
* -p <packagename>  Output code with the given package name
* -j                Output JsonProtocol for the generated classes

INFO: Either -f, -u or -s must be specified


Example usage:

```
%> sbt
> run -c MyClass -u http://127.0.0.1:9200/_cluster/stats
```

It will then generate case classes based on the types inferred from the downloaded document


Limitations
-----------

* Generated classes may require refactoring to fit your needs however it should compile as is.
* Number types are inferred based on the sample value, so they may be undermined (for example a Long can be mistyped as an Int if the actual value is less than Int.MaxValue).
* If the program encounters a null, it's typed as JsValue instead of an existing primitive. This is probably not the type you would expect.

Example
-------

Here is a sample output of the program based on a document from ElasticSearch (precisely the '/_cluster/stats' url output)

```scala
package com.frenchcoder.scalamones.elastic

import spray.json._

object MyClass {
  case class MyClassIndicesShardsIndexShards(min: Int, max: Int, avg: Int)
  case class MyClassIndicesShardsIndexPrimaries(min: Int, max: Int, avg: Int)
  case class MyClassIndicesShardsIndexReplication(min: Int, max: Int, avg: Int)
  case class MyClassIndicesShardsIndex(shards: MyClassIndicesShardsIndexShards, primaries: MyClassIndicesShardsIndexPrimaries, replication: MyClassIndicesShardsIndexReplication)
  case class MyClassIndicesShards(total: Int, primaries: Int, replication: Int, index: MyClassIndicesShardsIndex)
  case class MyClassIndicesFilterCache(memory_size_in_bytes: Int, evictions: Int)
  case class MyClassIndicesIdCache(memory_size_in_bytes: Int)
  case class MyClassIndicesDocs(count: Int, deleted: Int)
  case class MyClassIndicesCompletion(size_in_bytes: Int)
  case class MyClassIndicesStore(size_in_bytes: Int, throttle_time_in_millis: Int)
  case class MyClassIndicesPercolate(queries: Int, memory_size_in_bytes: Int, total: Int, memory_size: String, current: Int, time_in_millis: Int)
  case class MyClassIndicesSegments(count: Int, version_map_memory_in_bytes: Int, index_writer_max_memory_in_bytes: Int, fixed_bit_set_memory_in_bytes: Int, memory_in_bytes: Int, index_writer_memory_in_bytes: Int)
  case class MyClassIndicesFielddata(memory_size_in_bytes: Int, evictions: Int)
  case class MyClassIndices(shards: MyClassIndicesShards, count: Int, filter_cache: MyClassIndicesFilterCache, id_cache: MyClassIndicesIdCache, docs: MyClassIndicesDocs, completion: MyClassIndicesCompletion, store: MyClassIndicesStore, percolate: MyClassIndicesPercolate, segments: MyClassIndicesSegments, fielddata: MyClassIndicesFielddata)
  case class MyClassNodesJvmVersions(count: Int, vm_name: String, vm_vendor: String, version: String, vm_version: String)
  case class MyClassNodesJvmMem(heap_used_in_bytes: Int, heap_max_in_bytes: Int)
  case class MyClassNodesJvm(max_uptime_in_millis: Int, versions: Array[MyClassNodesJvmVersions], mem: MyClassNodesJvmMem, threads: Int)
  case class MyClassNodesCount(master_only: Int, master_data: Int, total: Int, client: Int, data_only: Int)
  case class MyClassNodesProcessCpu(percent: Int)
  case class MyClassNodesProcessOpenFileDescriptors(min: Int, max: Int, avg: Int)
  case class MyClassNodesProcess(cpu: MyClassNodesProcessCpu, open_file_descriptors: MyClassNodesProcessOpenFileDescriptors)
  case class MyClassNodesOsMem(total_in_bytes: Long)
  case class MyClassNodesOsCpu(count: Int, model: String, mhz: Int, total_sockets: Int, cache_size_in_bytes: Int, vendor: String, cores_per_socket: Int, total_cores: Int)
  case class MyClassNodesOs(available_processors: Int, mem: MyClassNodesOsMem, cpu: Array[MyClassNodesOsCpu])
  case class MyClassNodesFs(total_in_bytes: Long, free_in_bytes: Long, available_in_bytes: Long)
  case class MyClassNodes(jvm: MyClassNodesJvm, count: MyClassNodesCount, plugins: Array[Option[JsValue]], versions: Array[String], process: MyClassNodesProcess, os: MyClassNodesOs, fs: MyClassNodesFs)
  case class MyClass(cluster_name: String, indices: MyClassIndices, timestamp: Long, nodes: MyClassNodes, status: String)

  object MyClassProtocol extends DefaultJsonProtocol {
      implicit val myClassIndicesShardsIndexShardsFormat = jsonFormat3(MyClassIndicesShardsIndexShards)
      implicit val myClassIndicesShardsIndexPrimariesFormat = jsonFormat3(MyClassIndicesShardsIndexPrimaries)
      implicit val myClassIndicesShardsIndexReplicationFormat = jsonFormat3(MyClassIndicesShardsIndexReplication)
      implicit val myClassIndicesShardsIndexFormat = jsonFormat3(MyClassIndicesShardsIndex)
      implicit val myClassIndicesShardsFormat = jsonFormat4(MyClassIndicesShards)
      implicit val myClassIndicesFilterCacheFormat = jsonFormat2(MyClassIndicesFilterCache)
      implicit val myClassIndicesIdCacheFormat = jsonFormat1(MyClassIndicesIdCache)
      implicit val myClassIndicesDocsFormat = jsonFormat2(MyClassIndicesDocs)
      implicit val myClassIndicesCompletionFormat = jsonFormat1(MyClassIndicesCompletion)
      implicit val myClassIndicesStoreFormat = jsonFormat2(MyClassIndicesStore)
      implicit val myClassIndicesPercolateFormat = jsonFormat6(MyClassIndicesPercolate)
      implicit val myClassIndicesSegmentsFormat = jsonFormat6(MyClassIndicesSegments)
      implicit val myClassIndicesFielddataFormat = jsonFormat2(MyClassIndicesFielddata)
      implicit val myClassIndicesFormat = jsonFormat10(MyClassIndices)
      implicit val myClassNodesJvmVersionsFormat = jsonFormat5(MyClassNodesJvmVersions)
      implicit val myClassNodesJvmMemFormat = jsonFormat2(MyClassNodesJvmMem)
      implicit val myClassNodesJvmFormat = jsonFormat4(MyClassNodesJvm)
      implicit val myClassNodesCountFormat = jsonFormat5(MyClassNodesCount)
      implicit val myClassNodesProcessCpuFormat = jsonFormat1(MyClassNodesProcessCpu)
      implicit val myClassNodesProcessOpenFileDescriptorsFormat = jsonFormat3(MyClassNodesProcessOpenFileDescriptors)
      implicit val myClassNodesProcessFormat = jsonFormat2(MyClassNodesProcess)
      implicit val myClassNodesOsMemFormat = jsonFormat1(MyClassNodesOsMem)
      implicit val myClassNodesOsCpuFormat = jsonFormat8(MyClassNodesOsCpu)
      implicit val myClassNodesOsFormat = jsonFormat3(MyClassNodesOs)
      implicit val myClassNodesFsFormat = jsonFormat3(MyClassNodesFs)
      implicit val myClassNodesFormat = jsonFormat7(MyClassNodes)
      implicit val myClassFormat = jsonFormat5(MyClass)
  }
}
```