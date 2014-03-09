package de.uxnr.ts3.util;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Stack;
import java.util.Vector;

public class StringMap implements Serializable {
  private static final long serialVersionUID = 1695299775668648047L;
  private LinkedHashMap<String, Vector<String>> hashmap = null;

  public StringMap() {
    this.hashmap = new LinkedHashMap<String, Vector<String>>();
  }

  public void finalize() {
    this.hashmap.clear();
  }

  public String get(String key) {
    return this.hashmap.get(key).firstElement();
  }

  public String[] getArray(String key) {
    return this.castStringArray(this.hashmap.get(key).toArray());
  }

  public void set(String key, String value) {
    Stack<String> stack = new Stack<String>();
    stack.push(value);
    this.hashmap.put(key, stack);
  }

  public void setArray(String key, String[] values) {
    Stack<String> stack = new Stack<String>();
    stack.copyInto(values);
    this.hashmap.put(key, stack);
  }

  public void add(String key, String value) {
    Vector<String> stack = this.hashmap.get(key);
    if (stack == null) {
      stack = new Stack<String>();
    }
    stack.add(value);
    this.hashmap.put(key, stack);
  }

  public void addArray(String key, String[] values) {
    Vector<String> stack = this.hashmap.get(key);
    if (stack == null) {
      stack = new Stack<String>();
    }
    stack.copyInto(values);
    this.hashmap.put(key, stack);
  }

  public String[] keys() {
    return this.castStringArray(this.hashmap.keySet().toArray());
  }

  public int size() {
    return this.hashmap.size();
  }

  private String[] castStringArray(Object[] array) {
    String[] strings = new String[array.length];
    for (int i = 0; i < array.length; i++) {
      strings[i] = (String) array[i];
    }
    return strings;
  }

  public int hashCode() {
    return this.getClass().hashCode() ^ this.hashmap.hashCode();
  }
}
