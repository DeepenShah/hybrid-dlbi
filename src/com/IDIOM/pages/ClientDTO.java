package com.IDIOM.pages;

public class ClientDTO {

  private Integer id;
  private String name;
  private Integer parentId;
  private Boolean hasChild;

  public ClientDTO() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getCid() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public Boolean getHasChild() {
    return hasChild;
  }

  public void setHasChild(Boolean hasChild) {
    this.hasChild = hasChild;
  }

}
