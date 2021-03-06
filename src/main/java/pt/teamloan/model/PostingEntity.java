package pt.teamloan.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import pt.teamloan.model.enums.Intent;
import pt.teamloan.model.enums.PostingStatus;
import pt.teamloan.model.interfaces.UUIDMappeable;

/**
 * The persistent class for the posting database table.
 * 
 */
@Entity
@Table(name = "posting")
@Where(clause = "fl_deleted = false")
public class PostingEntity extends PanacheEntityBase implements Serializable, UUIDMappeable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "POSTING_ID_GENERATOR", sequenceName = "POSTING_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POSTING_ID_GENERATOR")
	@JsonbTransient
	private Integer id;

	@Type(type = "pg-uuid")
	private UUID uuid;

	private String email;

	@Enumerated(EnumType.STRING)
	private Intent intent;

	private String phone;

	@Enumerated(EnumType.STRING)
	private PostingStatus status = PostingStatus.ACTIVE;

	private String title;

	@Column(name = "zip_code")
	private String zipCode;

	@CreationTimestamp
	@Column(name = "created_at")
	private Timestamp createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "updated_status_at")
	private Timestamp updatedStatusAt;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_company")
	private CompanyEntity company;

	@OneToMany(mappedBy = "posting", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JsonbProperty("jobs")
	@Size(min = 1, max = 100)
	private List<PostingJobEntity> postingJobs;

	@JsonbTransient
	@Column(name = "fl_deleted")
	private boolean flDeleted = false;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_district")
	private DistrictEntity district;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_municipality")
	private MunicipalityEntity municipality;

	@Size(min = 1, max = 4000)
	private String notes;

	public PostingEntity() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	@JsonbTransient
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public PostingStatus getStatus() {
		return status;
	}

	public void setStatus(PostingStatus status) {
		this.status = status;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	@JsonbTransient
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Timestamp getUpdatedStatusAt() {
		return this.updatedStatusAt;
	}

	@JsonbTransient
	public void setUpdatedStatusAt(Timestamp updatedStatusAt) {
		this.updatedStatusAt = updatedStatusAt;
	}

	public UUID getUuid() {
		return this.uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public CompanyEntity getCompany() {
		return this.company;
	}

	public void setCompany(CompanyEntity company) {
		this.company = company;
	}

	public List<PostingJobEntity> getPostingJobs() {
		return this.postingJobs;
	}

	public void setPostingJobs(List<PostingJobEntity> postingJobs) {
		this.postingJobs = postingJobs;
		if (postingJobs != null && !postingJobs.isEmpty()) {
			for (PostingJobEntity postingJobEntity : postingJobs) {
				postingJobEntity.setPosting(this);
			}
		}
	}

	public PostingJobEntity addPostingJob(PostingJobEntity postingJob) {
		getPostingJobs().add(postingJob);
		postingJob.setPosting(this);

		return postingJob;
	}

	public PostingJobEntity removePostingJob(PostingJobEntity postingJob) {
		getPostingJobs().remove(postingJob);
		postingJob.setPosting(null);

		return postingJob;
	}

	public boolean isFlDeleted() {
		return flDeleted;
	}

	public void setFlDeleted(boolean flDeleted) {
		this.flDeleted = flDeleted;
	}

	public DistrictEntity getDistrict() {
		return this.district;
	}

	public void setDistrict(DistrictEntity district) {
		this.district = district;
	}

	public MunicipalityEntity getMunicipality() {
		return this.municipality;
	}

	public void setMunicipality(MunicipalityEntity municipality) {
		this.municipality = municipality;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}