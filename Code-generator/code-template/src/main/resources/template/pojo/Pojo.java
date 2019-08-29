package ${package_pojo};
import javax.persistence.*;
import java.io.Serializable;
<#list typeSet as set>
import ${set};
</#list>

@Table(name="${TableName}")
public class ${Table} implements Serializable{

<#list models as model>
	<#if model.id==true>
	@Id
	<#if model.identity=='YES'>
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	</#if>
	</#if>
    @Column(name = "${model.column}")
	private ${model.simpleType} ${model.name};//${model.desc!""}

</#list>


<#list models as model>
	//get方法
	public ${model.simpleType} get${model.upperName}() {
		return ${model.name};
	}

	//set方法
	public void set${model.upperName}(${model.simpleType} ${model.name}) {
		this.${model.name} = ${model.name};
	}
</#list>


}
