package com.resource.server.service.util;

import java.util.ArrayList;
import java.util.List;

public class NodeUtil<T> {
    private List<NodeUtil<T>> children = new ArrayList<NodeUtil<T>>();
    private NodeUtil<T> parent = null;
    private T data = null;

    public NodeUtil(T data) {
        this.data = data;
    }

    public NodeUtil(T data, NodeUtil<T> parent) {
        this.data = data;
        this.parent = parent;
    }

    public List<NodeUtil<T>> getChildren() {
        return children;
    }

    public void setParent(NodeUtil<T> parent) {
        parent.addChild(this);
        this.parent = parent;
    }

    public void addChild(T data) {
        NodeUtil<T> child = new NodeUtil<T>(data);
        child.setParent(this);
        this.children.add(child);
    }

    public void addChild(NodeUtil<T> child) {
        child.setParent(this);
        this.children.add(child);
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isLeaf() {
        return this.children.size() == 0;
    }

    public void removeParent() {
        this.parent = null;
    }
}
